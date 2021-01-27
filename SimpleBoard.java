import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDateTime;

public class SimpleBoard extends JFrame {

    private JPanel gameboardPanel, leftPanel, bottomLeftPanel, scoreBoard;    // top and bottom panels in the main window
    private JLabel timerLabel, player1Label, player2Label, player3Label, player4Label, playersTitle, movesTitle, bidOrderTitle, player1ScoreLabel, player2ScoreLabel, player3ScoreLabel, player4ScoreLabel;                // a text label to appear in the top panel
    private JButton instructionsButton, verifyButton, hintButton;
    private GridSquare[][] gridSquaresGameBoard, temporaryGameBoard;    // squares to appear in grid formation in the bottom panel
    private int x, y; // the size of the grid
    private JComboBox<Integer> movesList1, movesList2, movesList3, movesList4;
    private ImageIcon blueCircleIcon, blueHexIcon, blueSquareIcon, blueTriangleIcon, greenCircleIcon, greenHexIcon, greenSquareIcon, greenTriangleIcon;
    private ImageIcon redCircleIcon, redHexIcon, redSquareIcon, redTriangleIcon, yellowCircleIcon, yellowHexIcon, yellowSquareIcon, yellowTriangleIcon;
    private ImageIcon vortexIcon;
    private ImageIcon boardTileIcon;
    private TargetTile blueCircleTargetTile, blueHexTargetTile, blueSquareTargetTile, blueTriangleTargetTile;
    private TargetTile greenCircleTargetTile, greenHexTargetTile, greenSquareTargetTile, greenTriangleTargetTile;
    private TargetTile redCircleTargetTile, redHexTargetTile, redSquareTargetTile, redTriangleTargetTile;
    private TargetTile yellowCircleTargetTile, yellowHexTargetTile, yellowSquareTargetTile, yellowTriangleTargetTile;
    private TargetTile vortexTargetTile;
    private TargetChip blueCircleTargetChip, blueHexTargetChip, blueSquareTargetChip, blueTriangleTargetChip;
    private TargetChip greenCircleTargetChip, greenHexTargetChip, greenSquareTargetChip, greenTriangleTargetChip;
    private TargetChip redCircleTargetChip, redHexTargetChip, redSquareTargetChip, redTriangleTargetChip;
    private TargetChip yellowCircleTargetChip, yellowHexTargetChip, yellowSquareTargetChip, yellowTriangleTargetChip;
    private TargetChip vortexTargetChip;
    private TargetChip currentChipToDisplay;
    private TargetChip[] randomTargetChipArray;
    private ImageIcon redRobotIcon, greenRobotIcon, yellowRobotIcon, blueRobotIcon;
    private GridSquare currentTargetSquare;
    private RobotPieces redRobot, greenRobot, yellowRobot, blueRobot;
    private Player player1, player2, player3, player4, playerThatGoesNow;
    private JComboBox<Integer> bidOrderList1, bidOrderList2, bidOrderList3, bidOrderList4;
    private ClickRecorder clickRecorder;
    private boolean readyToVerify;
    private boolean winnerOfRoundFound, midMoveValid, needToMoveRobot;
    private int playerCount;
    private LocalDateTime startTime;
    private javax.swing.Timer timer;
    private Duration duration = Duration.ofMinutes(1);//TODO Fix this back.
    //CHANGE 14: Instead of a bid order system, it is more intuitive to let each user lock in their own bids separately.
    private JButton bidLock1, bidLock2, bidLock3, bidLock4;
    //CHANGE 15: A private integer is used to monitor the order of the bids
    private int bidCount;
    private int player1Score = 0, player2Score = 0, player3Score = 0, player4Score = 0, overallMoveCount;
    private GridSquare [] squaresClickedInOrderForMidMove;

    /*
     *  constructor method takes as input how many rows and columns of gridsquares to create
     *  it then creates the panels, their subcomponents and puts them all together in the main frame
     *  it sets up the full simple board with all of its specific barriers, target tile and robot
     *  locations
     *  it loads the game logic and the game begins when a player makes a bid
     *  it makes sure that the gui will be visible, centered and not resizable
     */
    public SimpleBoard(int x, int y) {
        this.x = x;
        this.y = y;
        //CHANGE 2: The size of the frame has been altered to minimize wasted real estate
        this.setSize(1080, 800);
        this.setTitle("Ricochet Robots - Simple Board");
        this.setLocationRelativeTo(null);
        this.readyToVerify = false;
        this.clickRecorder = new ClickRecorder();
        this.winnerOfRoundFound = false;
        this.playerCount = 1;
        this.currentChipToDisplay = new TargetChip("", "", null, -1, -1);
        this.randomTargetChipArray = new TargetChip[17];
        this.midMoveValid = false;
        this.needToMoveRobot = false;
        this.overallMoveCount = 0;
        this.temporaryGameBoard = new GridSquare [16][16];


        VerifyBidProcessor midMoveVerifier;

        createIconObjects();
        createPanels();
        createButtonsWithIconsAndTargetTilesAndAddToGrid();
        setupVisualAndLogicalBorders();
        addRobotIconsAndRobotsToStartingPositions();
        addPanelsToGetContentPane();
        enableAllGridSquaresClickable();
        menuBar();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        createPlayersAndConnectRobots();
        makeTargetChipsAndPutLatestOnBoard();
    }

    private void menuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menuBar.add(menu);
        JMenuItem menuItem1 = new JMenuItem(" New Game  ");
        menuItem1.addActionListener((ActionEvent e) -> {
            newGame();
        });
        JMenuItem menuItem2 = new JMenuItem(" Save  ");
        menuItem2.addActionListener((ActionEvent e) -> {
            saveGame();
        });
        menu.add(menuItem1);
        menu.add(menuItem2);
        this.setJMenuBar(menuBar);

    }

    private void createIconObjects() {
        //blue icons for board
        blueCircleIcon = new ImageIcon(this.getClass().getResource("/bluecircle.jpg"));
        blueHexIcon = new ImageIcon(this.getClass().getResource("/bluehex.jpg"));
        blueSquareIcon = new ImageIcon(this.getClass().getResource("/bluesquare.jpg"));
        blueTriangleIcon = new ImageIcon(this.getClass().getResource("/bluetriangle.jpg"));
        //green icons
        greenCircleIcon = new ImageIcon(this.getClass().getResource("greencircle.jpg"));
        greenHexIcon = new ImageIcon(this.getClass().getResource("/greenhex.jpg"));
        greenSquareIcon = new ImageIcon(this.getClass().getResource("/greensquare.jpg"));
        greenTriangleIcon = new ImageIcon(this.getClass().getResource("/greentriangle.jpg"));
        //red icons
        redCircleIcon = new ImageIcon(this.getClass().getResource("/redcircle.jpg"));
        redHexIcon = new ImageIcon(this.getClass().getResource("/redhex.jpg"));
        redSquareIcon = new ImageIcon(this.getClass().getResource("/redsquare.jpg"));
        redTriangleIcon = new ImageIcon(this.getClass().getResource("/redtriangle.jpg"));
        //yellow icons
        yellowCircleIcon = new ImageIcon(this.getClass().getResource("/yellowcircle.jpg"));
        yellowHexIcon = new ImageIcon(this.getClass().getResource("/yellowhex.jpg"));
        yellowSquareIcon = new ImageIcon(this.getClass().getResource("/yellowsquare.jpg"));
        yellowTriangleIcon = new ImageIcon(this.getClass().getResource("/yellowtriangle.jpg"));
        //CHANGE 1.1: Creation of the new icon menitoned above
        boardTileIcon = new ImageIcon(this.getClass().getResource("/boardtile.jpg"));
        vortexIcon = new ImageIcon(this.getClass().getResource("/vortex.jpeg"));
    }

    private void makeCopyOfGameBoard(GridSquare[][] originalGameBoard, GridSquare[][]copyOfGameBoard){
        for (int row = 0; row < 16; row++){
            for (int column = 0; column < 16; column++){
                copyOfGameBoard[row][column] = originalGameBoard[row][column];
            }
        }
    }

    private void createPanels() {
        // first create the panels
        leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(3, 1));//Changed so I can see score. was 5

        gameboardPanel = new JPanel();
        gameboardPanel.setLayout(new GridLayout(x, y));
        gameboardPanel.setSize(800, 800);
/* ===============================COMMENTED OUT MOVE ROBOTS DO NOT DELETE
        JButton needToMoveRobotButton = new JButton("N");
        needToMoveRobotButton.addActionListener((ActionEvent e) ->{
            clickRecorder = new ClickRecorder();
        });
        JButton doneMovingARobotButton = new JButton("D");
        doneMovingARobotButton.addActionListener((ActionEvent e) -> {
            //First check that they actually clicked first on a robot to move. If not, they did it wrong.
            this.squaresClickedInOrderForMidMove = clickRecorder.getArrayOfClickedSquares();
            VerifyBidProcessor midMoveVerifier = new VerifyBidProcessor(-1, this.squaresClickedInOrderForMidMove, this.squaresClickedInOrderForMidMove[0].getRobotOnSquare(), null, gridSquaresGameBoard);//TODO I assume they click the robot first.
            if (midMoveVerifier.wasThisInBetweenMovesLegal() == true){
                midMoveValid = true;
                this.overallMoveCount  += midMoveVerifier.countNumberOfMovesSimpleBoard(this.squaresClickedInOrderForMidMove);
                showMessageToUser("Valid move. Keep going.");
                moveRobotToNewPlace(this.squaresClickedInOrderForMidMove[0].getRobotOnSquare(), this.squaresClickedInOrderForMidMove);//Player must have clicked a robot at first.
            }
            else{
                midMoveValid = false;
                showMessageToUser("Not a valid move. Resetting board back at beginning of bid.");

            }
        });
        //doneMovingARobotButton.setEnabled(false);

        JButton verifyMultiRobotMoveBidButton = new JButton("V");
        verifyMultiRobotMoveBidButton.addActionListener((ActionEvent e) -> {
            this.squaresClickedInOrderForMidMove = clickRecorder.getArrayOfClickedSquares();
            VerifyBidProcessor midMoveVerifier = new VerifyBidProcessor(-1, this.squaresClickedInOrderForMidMove, this.squaresClickedInOrderForMidMove[0].getRobotOnSquare(), this.currentTargetSquare, gridSquaresGameBoard);//TODO //I assume they click the robot first.
            if (midMoveVerifier.wasLastMoveLegalAndAccurateForMidMoveBid() == true){
                this.overallMoveCount  += midMoveVerifier.countNumberOfMovesSimpleBoard(this.squaresClickedInOrderForMidMove);
                //Does number of moves match bid?
                if (midMoveVerifier.doesNumberOfMovesAndBidNumberMatch(this.overallMoveCount, this.playerThatGoesNow.getBidNumber()) == true){
                    midMoveValid = true;
                    boolean movesValid = true;

                    //NOW WHAT
                    finishBiddingProcessMovingOtherRobotsNeeded(movesValid, squaresClickedInOrderForMidMove[0].getRobotOnSquare(), this.squaresClickedInOrderForMidMove);

                }
               else{
                    midMoveValid = false;
                    showMessageToUser("Not a valid move. Resetting board back at beginning of bid.");
                    makeCopyOfGameBoard(this.temporaryGameBoard, this.gridSquaresGameBoard);
                }
            }
            else{
                midMoveValid = false;
                showMessageToUser("Not a valid move. Resetting board back at beginning of bid.");
            }
            ;});

*/ //=============================

        // then create the components for each panel and add them to it
        // for the top panel:
        timerLabel = new JLabel("Timer: 1 minute", SwingConstants.CENTER);
        //instructions button
        instructionsButton = new JButton("Instructions");
        instructionsButton.addActionListener((ActionEvent e) -> {
            showInstructions();
        });
        //hint button (removed for now for gui)
        hintButton = new JButton("Get a hint!");
        hintButton.addActionListener((ActionEvent e) -> {
            showHint();
        });
        //verify bid button
        verifyButton = new JButton("Verify Bid!");
        verifyButton.addActionListener((ActionEvent e ) -> {
            finishBiddingProcessNoMovingOtherRobotsNeeded();});
        //verifyButton.addActionListener((ActionEvent e) -> {
        //    this.readyToVerify = true;
        //});
        //create a scoreboard to track each players score
        scoreBoard = new JPanel(new GridLayout(5, 2, 3, 3));
        scoreBoard.setSize(200, 200);
        JLabel scoreLabel = new JLabel("SCORE:", SwingConstants.CENTER);
        player1ScoreLabel = new JLabel("" + player1Score, SwingConstants.CENTER);
        player2ScoreLabel = new JLabel("" + player2Score, SwingConstants.CENTER);
        player3ScoreLabel = new JLabel("" + player3Score, SwingConstants.CENTER);
        player4ScoreLabel = new JLabel("" + player4Score, SwingConstants.CENTER);

        //Labels for the Number of Players
        playersTitle = new JLabel("PLAYERS:", SwingConstants.CENTER);
        player1Label = new JLabel("Player 1:", SwingConstants.CENTER);
        player2Label = new JLabel("Player 2:", SwingConstants.CENTER);
        player3Label = new JLabel("Player 3:", SwingConstants.CENTER);
        player4Label = new JLabel("Player 4:", SwingConstants.CENTER);
        //Adding all the components
        scoreBoard.add(playersTitle);
        scoreBoard.add(scoreLabel);

        scoreBoard.add(player1Label);
        scoreBoard.add(player1ScoreLabel);

        scoreBoard.add(player2Label);
        scoreBoard.add(player2ScoreLabel);

        scoreBoard.add(player3Label);
        scoreBoard.add(player3ScoreLabel);

        scoreBoard.add(player4Label);
        scoreBoard.add(player4ScoreLabel);

        JPanel uppertopLeftPanel = new JPanel();
        JPanel midtopLeftPanel = new JPanel();
        JPanel lowertopLeftPanel = new JPanel();
        uppertopLeftPanel.add(instructionsButton);
        midtopLeftPanel.add(timerLabel);
        lowertopLeftPanel.add(verifyButton);
        leftPanel.add(uppertopLeftPanel);
        //leftPanel.add(hintButton);
        //leftPanel.add(needToMoveRobotButton);
        //leftPanel.add(doneMovingARobotButton);
        //leftPanel.add(verifyMultiRobotMoveBidButton);
        leftPanel.add(midtopLeftPanel);
        leftPanel.add(lowertopLeftPanel);
        leftPanel.add(scoreBoard);
        //timer for the bid round
        timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDateTime now = LocalDateTime.now();
                Duration runningTime = Duration.between(startTime, now);
                Duration timeLeft = duration.minus(runningTime);
                if (timeLeft.isZero() || timeLeft.isNegative()) {
                    timeLeft = Duration.ZERO;
                    bidLock1.setEnabled(false);
                    movesList1.setEnabled(false);
                    bidLock2.setEnabled(false);
                    movesList2.setEnabled(false);
                    bidLock3.setEnabled(false);
                    movesList3.setEnabled(false);
                    bidLock4.setEnabled(false);
                    movesList4.setEnabled(false);
                    theBidWinnerPlays();
                    timer.stop();

                }
                timerLabel.setText(format(timeLeft));
            }
        });
        bottomLeftPanel = new JPanel(new GridLayout(5, 3, 3, 3));
        bottomLeftPanel.setSize(100, 400);

        movesTitle = new JLabel("BID:", SwingConstants.CENTER);
        movesList1 = new JComboBox<Integer>();
        movesList2 = new JComboBox<Integer>();
        movesList3 = new JComboBox<Integer>();
        movesList4 = new JComboBox<Integer>();

        for (int i = 0; i < 31; i++) {
            movesList1.addItem(i);
            movesList2.addItem(i);
            movesList3.addItem(i);
            movesList4.addItem(i);
        }

        bidOrderTitle = new JLabel("", SwingConstants.CENTER);
        bidCount = 1;
        bidLock1 = new JButton("Lock my Bid");
        bidLock1.addActionListener((ActionEvent e) ->
        {
            player1.setBidOrder(bidCount);
            if (bidCount == 1) {
                startTime = LocalDateTime.now();
                timer.start();

            }
           bidCount++;
        });

        bidLock2 = new JButton("Lock my Bid");
        bidLock2.addActionListener((ActionEvent e) ->
        {
            player2.setBidOrder(bidCount);
            if (bidCount == 1) {
                startTime = LocalDateTime.now();
                timer.start();
            }
            bidCount++;
        });

        bidLock3 = new JButton("Lock my Bid");
        bidLock3.addActionListener((ActionEvent e) ->
        {
            player3.setBidOrder(bidCount);
            if (bidCount == 1) {
                startTime = LocalDateTime.now();
                timer.start();
            }
            bidCount++;
        });

        bidLock4 = new JButton("Lock my Bid");
        bidLock4.addActionListener((ActionEvent e) ->
        {
            player4.setBidOrder(bidCount);
            if (bidCount == 1) {
                startTime = LocalDateTime.now();
                timer.start();
           }
            bidCount++;
        });

        //Labels for the Number of Players
        playersTitle = new JLabel("PLAYERS:", SwingConstants.CENTER);
        player1Label = new JLabel("Player 1:", SwingConstants.CENTER);
        player2Label = new JLabel("Player 2:", SwingConstants.CENTER);
        player3Label = new JLabel("Player 3:", SwingConstants.CENTER);
        player4Label = new JLabel("Player 4:", SwingConstants.CENTER);
        //Adding all the components
        bottomLeftPanel.add(playersTitle);
        bottomLeftPanel.add(movesTitle);
        bottomLeftPanel.add(bidOrderTitle);

        bottomLeftPanel.add(player1Label);
        bottomLeftPanel.add(movesList1);
        bottomLeftPanel.add(bidLock1);

        bottomLeftPanel.add(player2Label);
        bottomLeftPanel.add(movesList2);
        bottomLeftPanel.add(bidLock2);

        bottomLeftPanel.add(player3Label);
        bottomLeftPanel.add(movesList3);
        bottomLeftPanel.add(bidLock3);

        bottomLeftPanel.add(player4Label);
        bottomLeftPanel.add(movesList4);
        bottomLeftPanel.add(bidLock4);


    }

    private void reEnableBids() {
        //reset bids for each round of play
        bidLock1.setEnabled(true);
        movesList1.setEnabled(true);
        movesList1.setSelectedIndex(0);
        bidLock2.setEnabled(true);
        movesList2.setEnabled(true);
        movesList2.setSelectedIndex(0);
        bidLock3.setEnabled(true);
        movesList3.setEnabled(true);
        movesList3.setSelectedIndex(0);
        bidLock4.setEnabled(true);
        movesList4.setEnabled(true);
        movesList4.setSelectedIndex(0);


    }

    private void showMessageToUser(String aMessage){
        JOptionPane proveBid = new JOptionPane();
        proveBid.showMessageDialog(new JFrame(), aMessage);
    }

    private void addPanelsToGetContentPane() {
        // now add the panels to the main frame
        getContentPane().setLayout(new BorderLayout());
        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        JPanel bottomPanelHolder = new JPanel();
        JPanel topLeftPanelHolder = new JPanel();
        JPanel bottomLeftPanelHolder = new JPanel();
        leftPanel.add(topLeftPanelHolder);
        leftPanel.add(bottomLeftPanelHolder);
        topLeftPanelHolder.add(this.leftPanel);
        bottomLeftPanelHolder.add(bottomLeftPanel);
        getContentPane().add(leftPanel, BorderLayout.EAST);
        getContentPane().add(bottomPanelHolder, BorderLayout.CENTER);
        bottomPanelHolder.add(gameboardPanel);     // needs to be center or will draw too small
    }

    private void createPlayersAndConnectRobots() {
        //creates four player objects and assigns a string to represent each one
        player1 = new Player();
        player2 = new Player();
        player3 = new Player();
        player4 = new Player();
        //this is done in this version as it does not have the UI functionality
        player1.setPlayerTypeToHuman();
        player2.setPlayerTypeToHuman();
        player3.setPlayerTypeToHuman();
        player4.setPlayerTypeToHuman();
        //sets the robot to the player (We know later that players don't own robots, but we left this in for now to ensure code works.)
        player1.setPlayersRobot(redRobot);
        player2.setPlayersRobot(greenRobot);
        player3.setPlayersRobot(yellowRobot);
        player4.setPlayersRobot(blueRobot);
        //assigns each object to a string value
        player1.setPlayerName("Player 1");
        player2.setPlayerName("Player 2");
        player3.setPlayerName("Player 3");
        player4.setPlayerName("Player 4");
    }

    protected String format(Duration duration) {
        //this is used for the timer for display purposes
        long hours = duration.toHours();
        long mins = duration.minusHours(hours).toMinutes();
        long seconds = duration.minusMinutes(mins).toMillis() / 1000;
        return String.format("Timer: %02d seconds", seconds);
    }

    //This method stores the bids entered by each player to each player object and prompts the winner of the bid to play.
    private void theBidWinnerPlays() {
        //Lock in the bids and the bid order.

        player1.setBidNumber(movesList1.getSelectedIndex());
        player2.setBidNumber(movesList2.getSelectedIndex());
        player3.setBidNumber(movesList3.getSelectedIndex());
        player4.setBidNumber(movesList4.getSelectedIndex());

        //First check that no player claimed the same number of bids
        // if (didMoreThanOnePlayerClaimSameBidOrder() == true){
        //     String message = "Sorry, someone claimed to bid at the same time as another. Fix your order, and lock in bids again using the button.";
        //     JOptionPane wrongOrder = new JOptionPane();
        //     wrongOrder.showMessageDialog(new JFrame(), message);

        // }
        //else{

        //Determine the player with the lowest bids AND who bid first if there are duplicate bids.
        playerThatGoesNow = determinePlayerThatShowsMovesNow();

        //Now check if there are others with the same bid
        //Let the users know who had the lowest bid and instruct
        //them to demonstrate their bid
        String message = "Ok, so " + playerThatGoesNow.getPlayerName() + ", click your proposed path and then click Verify.";
        JOptionPane proveBid = new JOptionPane();
        proveBid.showMessageDialog(new JFrame(), message);

/*
        showMoveRobotMessage();
*/

        this.clickRecorder = new ClickRecorder();
        addActionListenersToAllGridSquares();
        //verifyButton.addActionListener((ActionEvent e) -> {
         //   finishBiddingProcess();
       // });
    }

    private RobotPieces calculateRobotRequiredToMove() {
        //figure out which robot is in play each round based on the color of the
        //target square in the center of the board
        RobotPieces robotToMove = null;
        String color = this.currentTargetSquare.getTargetTileOnSquare().getColor();
        if (color.equals("RED")) {
            robotToMove = redRobot; //t
        } else if (color.equals("GREEN")) {
            robotToMove = greenRobot;
        } else if (color.equals("BLUE")) {
            robotToMove = blueRobot;
        } else {
            robotToMove = yellowRobot;
        }
        return robotToMove;
    }

/*
    private void showMoveRobotMessage(){
        String message = "Ok, so " + playerThatGoesNow.getPlayerName() + ", do you need to move other robots during your bid demonstration?\n" +
                "If yes, click I need to move robot, move the robot, then click done moving robot, and repeat if needed.\nOn the last step, move the robot, and click verify bid.";
        //JOptionPane questionToMoveOtherRobots = new JOptionPane();
        Object[] options = {"Yes", "No"};
        int theirAnswerChoice = JOptionPane.showOptionDialog(new JFrame(), message, "Need To Move Other Robots During Demo?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

        if (theirAnswerChoice == 0){
            needToMoveRobot = true;
            //Now make a temporary backup copy of gameboard
            makeCopyOfGameBoard(this.gridSquaresGameBoard, this.temporaryGameBoard);
        }
        else{
            needToMoveRobot = false;
        }

    }

 */

/*
    private void finishBiddingProcessMovingOtherRobotsNeeded(Boolean areMovesValid, RobotPieces theRobotThatsMoving, GridSquare[] squaresClicked) {
        if (areMovesValid == true){
        this.winnerOfRoundFound = true;
        String message = "Correct bid! " + playerThatGoesNow.getPlayerName() + " won this round!";
        JOptionPane messageThatPlayerWon = new JOptionPane();
        messageThatPlayerWon.showMessageDialog(new JFrame(), message);
        this.playerCount = 1; //reset to one.
        this.bidCount = 1; //reset to one.
        if (playerThatGoesNow.getPlayerName() == "Player 1") {
            player1Score++;
            player1ScoreLabel.setText("" + player1Score);
        } else if (playerThatGoesNow.getPlayerName() == "Player 2") {
            player2Score++;
            player2ScoreLabel.setText("" + player2Score);
        } else if (playerThatGoesNow.getPlayerName() == "Player 3") {
            player3Score++;
            player3ScoreLabel.setText("" + player3Score);
        } else if (playerThatGoesNow.getPlayerName() == "Player 4") {
            player4Score++;
            player4ScoreLabel.setText("" + player4Score);
        }
        if (player1Score == 5) {
            JFrame frame = new JFrame();
            Object[] options = {"New Game",
                    "Exit"};
            int answer = JOptionPane.showOptionDialog(frame,
                    "Congratulation Player 1! "
                            + "You are the winner!",
                    "Game Over!",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[1]);
            if (answer == JOptionPane.YES_OPTION) {
                super.dispose();
                ChooseDisplayGUI newGame = new ChooseDisplayGUI();
            } else if (answer == JOptionPane.NO_OPTION) {
                setDefaultCloseOperation(JOptionPane.NO_OPTION);
                super.dispose();
            }
        }
        if (player2Score == 5) {
            JFrame frame = new JFrame();
            Object[] options = {"New Game",
                    "Exit"};
            int answer = JOptionPane.showOptionDialog(frame,
                    "Congratulation Player 2! "
                            + "You are the winner!",
                    "Game Over!",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[1]);
            if (answer == JOptionPane.YES_OPTION) {
                super.dispose();
                ChooseDisplayGUI newGame = new ChooseDisplayGUI();
            } else if (answer == JOptionPane.NO_OPTION) {
                setDefaultCloseOperation(JOptionPane.NO_OPTION);
                super.dispose();
            }
        }
        if (player3Score == 5) {
            JFrame frame = new JFrame();
            Object[] options = {"New Game",
                    "Exit"};
            int answer = JOptionPane.showOptionDialog(frame,
                    "Congratulation Player 3! "
                            + "You are the winner!",
                    "Game Over!",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[1]);
            if (answer == JOptionPane.YES_OPTION) {
                super.dispose();
                ChooseDisplayGUI newGame = new ChooseDisplayGUI();
            } else if (answer == JOptionPane.NO_OPTION) {
                setDefaultCloseOperation(JOptionPane.NO_OPTION);
                super.dispose();
            }
        }
        if (player4Score == 5) {
            JFrame frame = new JFrame();
            Object[] options = {"New Game",
                    "Exit"};
            int answer = JOptionPane.showOptionDialog(frame,
                    "Congratulation Player 4! "
                            + "You are the winner!",
                    "Game Over!",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[1]);
            if (answer == JOptionPane.YES_OPTION) {
                super.dispose();
                ChooseDisplayGUI newGame = new ChooseDisplayGUI();
            } else if (answer == JOptionPane.NO_OPTION) {
                setDefaultCloseOperation(JOptionPane.NO_OPTION);
                super.dispose();
            }
        }
        //Show on grid to remove
        this.clickRecorder.clearClickedSquares();
        //Then move robot
        moveRobotToNewPlace(theRobotThatsMoving, squaresClicked);
        giveTargetChipPointToPlayerAndRemoveTargetTileFromBoard(playerThatGoesNow, squaresClicked);
        //moveWinningBiddersRobot(arrayOfSquaresPlayerClickedInOrder);
        giveTargetChipPointToPlayerAndRemoveTargetTileFromBoard(playerThatGoesNow, squaresClicked);
        updateNewTargetChipOnBoardAndSquareCoordinates();
        timerLabel.setText("Timer: 1 minute");
        reEnableBids();//Maybe add later.
        // we need to add here a reset of player bids, orders, the timer (new round of play)
    } else {
            if (this.playerCount == 4) {
                String message = "Nobody made accurate bids. Begin bidding round again.";
                JOptionPane beginAgain = new JOptionPane();
                beginAgain.showMessageDialog(new JFrame(), message);
                this.playerCount = 1;//Reset to 1.
                this.bidCount = 1; //reset to one.
                reEnableBids();
            } else {
                this.winnerOfRoundFound = false;
                Player lostPlayer = playerThatGoesNow;
                //String message = playerThatGoesNow.getPlayerName() + ", sorry, that's not correct. Next player.";
                //JOptionPane incorrectBid = new JOptionPane();
                //incorrectBid.showMessageDialog(new JFrame(), message);
                playerThatGoesNow.setBidNumber(1000000); //Leave loser player out of next finding out next bid.
                playerThatGoesNow.setBidOrder(10000000); //Leave loser player out of next finding out next bid.
                this.playerCount += 1;
                playerThatGoesNow = determinePlayerThatShowsMovesNow();
                this.clickRecorder.clearClickedSquares();

                String message = lostPlayer.getPlayerName() + ", sorry, that's not correct. " + playerThatGoesNow.getPlayerName() + ", please show you moves and click Verify Bid.";
                JOptionPane incorrectBid = new JOptionPane();
                incorrectBid.showMessageDialog(new JFrame(), message);
            }

    }}
*/
    private void finishBiddingProcessNoMovingOtherRobotsNeeded() {
        //this method works as a driver of the game and also keeps track of the score
        //each round it verifies the bid and determines the winner
        //the winner gets a point added to their score
        //the game ends when the first player gets a score of 5
        removeAddedActionListenersToAllGridSquares();
        GridSquare[] arrayOfSquaresPlayerClickedInOrder = clickRecorder.getArrayOfClickedSquares();
        //GridSquare squareWithCurrentTargetTile = gridSquaresGameBoard[13][5]; //It will be this one for now the red hex star one.
        RobotPieces robotToMove = calculateRobotRequiredToMove();

        VerifyBidProcessor verifyBidProcessor = new VerifyBidProcessor(playerThatGoesNow.getBidNumber(), arrayOfSquaresPlayerClickedInOrder, robotToMove, this.currentTargetSquare, gridSquaresGameBoard);

        if ( (verifyBidProcessor.wereMovesLegalAndAccurate() == true)   ) {
            this.winnerOfRoundFound = true;
            String message = "Correct bid! " + playerThatGoesNow.getPlayerName() + " won this round!";
            JOptionPane messageThatPlayerWon = new JOptionPane();
            messageThatPlayerWon.showMessageDialog(new JFrame(), message);
            this.playerCount = 1; //reset to one.
            this.bidCount = 1; //reset to one.
            if (playerThatGoesNow.getPlayerName() == "Player 1") {
                player1Score++;
                player1ScoreLabel.setText("" + player1Score);
            } else if (playerThatGoesNow.getPlayerName() == "Player 2") {
                player2Score++;
                player2ScoreLabel.setText("" + player2Score);
            } else if (playerThatGoesNow.getPlayerName() == "Player 3") {
                player3Score++;
                player3ScoreLabel.setText("" + player3Score);
            } else if (playerThatGoesNow.getPlayerName() == "Player 4") {
                player4Score++;
                player4ScoreLabel.setText("" + player4Score);
            }
            //this decides the winner of the game
            //as soon as a player gets 5 points, the game
            //ends
            if (player1Score == 5) {
                JFrame frame = new JFrame();
                Object[] options = {"New Game",
                        "Exit"};
                int answer = JOptionPane.showOptionDialog(frame,
                        "Congratulation Player 1! "
                                + "You are the winner!",
                        "Game Over!",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        options,
                        options[1]);
                if (answer == JOptionPane.YES_OPTION) {
                    super.dispose();
                    ChooseDisplayGUI newGame = new ChooseDisplayGUI();
                } else if (answer == JOptionPane.NO_OPTION) {
                    setDefaultCloseOperation(JOptionPane.NO_OPTION);
                    super.dispose();
                }
            }
            if (player2Score == 5) {
                JFrame frame = new JFrame();
                Object[] options = {"New Game",
                        "Exit"};
                int answer = JOptionPane.showOptionDialog(frame,
                        "Congratulation Player 2! "
                                + "You are the winner!",
                        "Game Over!",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        options,
                        options[1]);
                if (answer == JOptionPane.YES_OPTION) {
                    super.dispose();
                    ChooseDisplayGUI newGame = new ChooseDisplayGUI();
                } else if (answer == JOptionPane.NO_OPTION) {
                    setDefaultCloseOperation(JOptionPane.NO_OPTION);
                    super.dispose();
                }
            }
            if (player3Score == 5) {
                JFrame frame = new JFrame();
                Object[] options = {"New Game",
                        "Exit"};
                int answer = JOptionPane.showOptionDialog(frame,
                        "Congratulation Player 3! "
                                + "You are the winner!",
                        "Game Over!",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        options,
                        options[1]);
                if (answer == JOptionPane.YES_OPTION) {
                    super.dispose();
                    ChooseDisplayGUI newGame = new ChooseDisplayGUI();
                } else if (answer == JOptionPane.NO_OPTION) {
                    setDefaultCloseOperation(JOptionPane.NO_OPTION);
                    super.dispose();
                }
            }
            if (player4Score == 5) {
                JFrame frame = new JFrame();
                Object[] options = {"New Game",
                        "Exit"};
                int answer = JOptionPane.showOptionDialog(frame,
                        "Congratulation Player 4! "
                                + "You are the winner!",
                        "Game Over!",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        options,
                        options[1]);
                if (answer == JOptionPane.YES_OPTION) {
                    super.dispose();
                    ChooseDisplayGUI newGame = new ChooseDisplayGUI();
                } else if (answer == JOptionPane.NO_OPTION) {
                    setDefaultCloseOperation(JOptionPane.NO_OPTION);
                    super.dispose();
                }
            }

            this.clickRecorder.clearClickedSquares();
            //Then move robot
            moveRobotToNewPlace(robotToMove, arrayOfSquaresPlayerClickedInOrder);
            giveTargetChipPointToPlayerAndRemoveTargetTileFromBoard(playerThatGoesNow, arrayOfSquaresPlayerClickedInOrder);
            //moveWinningBiddersRobot(arrayOfSquaresPlayerClickedInOrder);
            giveTargetChipPointToPlayerAndRemoveTargetTileFromBoard(playerThatGoesNow, arrayOfSquaresPlayerClickedInOrder);
            updateNewTargetChipOnBoardAndSquareCoordinates();
            timerLabel.setText("Timer: 1 minute");
            reEnableBids();

        } else {
            //case where all players bids are incorrect and no one is awarded a score
            if (this.playerCount == 4) {
                String message = "Nobody made accurate bids. Begin bidding round again.";
                JOptionPane beginAgain = new JOptionPane();
                beginAgain.showMessageDialog(new JFrame(), message);
                this.playerCount = 1;//Reset to 1.
                this.bidCount = 1; //reset to one.
                reEnableBids();
            } else {
                this.winnerOfRoundFound = false;
                Player lostPlayer = playerThatGoesNow;
                //String message = playerThatGoesNow.getPlayerName() + ", sorry, that's not correct. Next player.";
                //JOptionPane incorrectBid = new JOptionPane();
                //incorrectBid.showMessageDialog(new JFrame(), message);
                playerThatGoesNow.setBidNumber(1000000); //Leave loser player out of next finding out next bid.
                playerThatGoesNow.setBidOrder(10000000); //Leave loser player out of next finding out next bid.
                this.playerCount += 1;
                playerThatGoesNow = determinePlayerThatShowsMovesNow();
                this.clickRecorder.clearClickedSquares();

                String message = lostPlayer.getPlayerName() + ", sorry, that's not correct. " + playerThatGoesNow.getPlayerName() + ", please show you moves and click Verify Bid.";
                JOptionPane incorrectBid = new JOptionPane();
                incorrectBid.showMessageDialog(new JFrame(), message);
            }

        }
    }
        private Player determinePlayerThatShowsMovesNow () {
            //find out who made the lowest bid
            int currentLowestBid = player1.getBidNumber();
            Player currentPlayerWithLowestBid = player1;

            if (currentLowestBid > player2.getBidNumber()) {
                currentLowestBid = player2.getBidNumber();
                currentPlayerWithLowestBid = player2;
            }

            if (currentLowestBid > player3.getBidNumber()) {
                currentLowestBid = player3.getBidNumber();
                currentPlayerWithLowestBid = player3;
            }

            if (currentLowestBid > player4.getBidNumber()) {
                currentLowestBid = player4.getBidNumber();
                currentPlayerWithLowestBid = player4;
            }

            //Now check if there is anyone that has the same lowest bid number.
            //If there is, get their bid order. The one who bid first goes first.

            Player currentPlayerToReturn = currentPlayerWithLowestBid;
            int currentBidOrder = currentPlayerToReturn.getBidOrder();
            if ((currentPlayerToReturn.getBidNumber() == player1.getBidNumber()) && (currentPlayerWithLowestBid.getBidOrder() > player1.getBidOrder())) {
                currentPlayerToReturn = player1;
            }

            if ((currentPlayerToReturn.getBidNumber() == player2.getBidNumber()) && (currentPlayerWithLowestBid.getBidOrder() > player2.getBidOrder())) {
                currentPlayerToReturn = player2;
            }

            if ((currentPlayerToReturn.getBidNumber() == player3.getBidNumber()) && (currentPlayerWithLowestBid.getBidOrder() > player3.getBidOrder())) {
                currentPlayerToReturn = player3;
            }

            if ((currentPlayerToReturn.getBidNumber() == player4.getBidNumber()) && (currentPlayerWithLowestBid.getBidOrder() > player4.getBidOrder())) {
                currentPlayerToReturn = player4;
            }

            return currentPlayerToReturn;
        }

        private void enableAllGridSquaresClickable () {
            //enable every button of the board
            for (int row = 0; row < this.gridSquaresGameBoard.length; row++) {
                for (int column = 0; column < this.gridSquaresGameBoard[row].length; column++) {
                    this.gridSquaresGameBoard[row][column].setEnabled(true);
                }
            }
        }

        private void addActionListenersToAllGridSquares () {
            //add action listeneres to every button on the board
            for (int row = 0; row < this.gridSquaresGameBoard.length; row++) {
                for (int column = 0; column < this.gridSquaresGameBoard[row].length; column++) {
                    int finalRow = row;
                    int finalColumn = column;
                    this.gridSquaresGameBoard[finalRow][finalColumn].addActionListener((ActionEvent e) -> {
                        clickRecorder.recordClickedGridSquare(this.gridSquaresGameBoard[finalRow][finalColumn]);
                    });
                }
            }
        }

        private void removeAddedActionListenersToAllGridSquares () {
            //remove action listeners from each button on the board
            for (int row = 0; row < this.gridSquaresGameBoard.length; row++) {
                for (int column = 0; column < this.gridSquaresGameBoard[row].length; column++) {
                    int finalRow = row;
                    int finalColumn = column;

                    this.gridSquaresGameBoard[finalRow][finalColumn].removeActionListener((ActionEvent e) -> {
                        clickRecorder.recordClickedGridSquare(this.gridSquaresGameBoard[finalRow][finalColumn]);
                    });
                }
            }

        }

    private void moveRobotToNewPlace(RobotPieces robotToMove, GridSquare [] playersClickedSquares){ //JUNAID LOOK AT THIS!
        //RobotPieces playersRobot = playerThatGoesNow.getPlayersRobot();
        //Get the gridsquare the robot is on.
        //Get robot's icon on the gridsquare
        int squareRowCoord = playersClickedSquares[0].getSquaresRowCoordinate();
        int squareColumnCoord = playersClickedSquares[0].getSquaresColumnCoordinate();

        Icon robotIcon = gridSquaresGameBoard[squareRowCoord][squareColumnCoord].getIcon();
        Icon temporaryIcon = new ImageIcon(boardTileIcon.getImage().getScaledInstance(35, 35, java.awt.Image.SCALE_SMOOTH));

        for (int i = 1; i < playersClickedSquares.length; i++) {

            //Remove the icon from the gridsquare and remove robot from the square
            gridSquaresGameBoard[squareRowCoord][squareColumnCoord].setIcon(temporaryIcon);
            gridSquaresGameBoard[squareRowCoord][squareColumnCoord].setVisible(true);
            gridSquaresGameBoard[squareRowCoord][squareColumnCoord].removeRobotFromSquare();

            //Get new coordinates
            squareRowCoord = playersClickedSquares[i].getSquaresRowCoordinate();
            squareColumnCoord = playersClickedSquares[i].getSquaresColumnCoordinate();

            temporaryIcon = gridSquaresGameBoard[squareRowCoord][squareColumnCoord].getIcon();
            gridSquaresGameBoard[squareRowCoord][squareColumnCoord].setIcon(robotIcon);
            gridSquaresGameBoard[squareRowCoord][squareColumnCoord].setVisible(true);

            //update now the robot's own coordinates
            robotToMove.setRobotRowCoordinate(squareRowCoord);
            robotToMove.setRobotColumnCoordinate(squareColumnCoord);

            //Put robot onto the square by giving the square the robot //I ADDED THIS LINE APR1
            gridSquaresGameBoard[squareRowCoord][squareColumnCoord].addRobotToSquare(robotToMove);

        }
    }

//test
        private void updateNewTargetChipOnBoardAndSquareCoordinates () {
            //every round there is a target square displayed in the center of
            //the board. this method decides which target square is displayed next
            //and updates the icons at the center of the board
            boolean nextChipFound = false;
            int index = 0;
            while (nextChipFound == false) {
                if (this.randomTargetChipArray[index] != null) {
                    this.currentChipToDisplay = this.randomTargetChipArray[index];
                    this.randomTargetChipArray[index] = null;
                    nextChipFound = true;
                } else {
                    index += 1;
                }
            }
            ImageIcon iconToUpdate = this.currentChipToDisplay.getImageIcon();

            //Update the icon on the four squares on the board.
            //Square 1
            //7.7,9.9 are the center coordinates
            for (int i = 7; i < 9; i++) {
                for (int j = 7; j < 9; j++)
                    gridSquaresGameBoard[i][j].setIcon(new ImageIcon(iconToUpdate.getImage().getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH)));
            }
            //Now find the square that matches
            this.currentTargetSquare = gridSquaresGameBoard[this.currentChipToDisplay.getRelatedGridSquaresRowCoord()][this.currentChipToDisplay.getRelatedGridSquaresColumnCoord()];
        }



        private void showInstructions () {
            String message = "***Instructions To Be Added Later.***";
            JOptionPane showInstructions = new JOptionPane();
            showInstructions.showMessageDialog(new JFrame(), message);
        }

        private void showHint () {
            //this method was not implented in this version of the game
            String message = "***Hint options To Be Added Later.***";
            JOptionPane showInstructions = new JOptionPane();
            showInstructions.showMessageDialog(new JFrame(), message);
        }

        private void saveGame () {
            String message = "***Save Game Option to be added later.***";
            JOptionPane saveGame = new JOptionPane();
            saveGame.showMessageDialog(new JFrame(), message);
        }

        private void newGame () {
            //allows the user to start a new game at any time
            //the option to save was not implented in this version
            JFrame frame = new JFrame();
            Object[] options = {"New Game",
                    "Save",
                    "Cancel"};
            int answer = JOptionPane.showOptionDialog(frame,
                    "Do you want to save your progress? ",
                    "New Game!",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[2]);
            if (answer == JOptionPane.YES_OPTION) {
                super.dispose();
                ChooseDisplayGUI newGame = new ChooseDisplayGUI();
            } else if (answer == JOptionPane.NO_OPTION) {
                saveGame();
            } else if (answer == JOptionPane.CANCEL_OPTION) {
                setDefaultCloseOperation(JOptionPane.CANCEL_OPTION);
            }
        }

        private void giveTargetChipPointToPlayerAndRemoveTargetTileFromBoard (Player playerThatWonBid, GridSquare [] playersClickedSquares){
            //whoever wins the round is awarded the target square in the form on a point and that tile is
            //removed from the list of remaining tiles to be collected
            playerThatWonBid.addTargetChipToPlayersCollection(currentChipToDisplay);
            GridSquare squareWithTile = playersClickedSquares[playersClickedSquares.length - 1];
            //To ensure, remove it from the source, the actual Gameboard [][]
            this.gridSquaresGameBoard[squareWithTile.getSquaresRowCoordinate()][squareWithTile.getSquaresColumnCoordinate()].removeTargetTileFromGridSquare();
        }


        private void setupVisualAndLogicalBorders () {
            //set the exterior barriers visually
            gridSquaresGameBoard[0][1].setBorder(BorderFactory.createMatteBorder(4, 1, 1, 6, Color.GRAY));
            gridSquaresGameBoard[0][1].addEastEdgeBarrier();
            gridSquaresGameBoard[0][2].setBorder(BorderFactory.createMatteBorder(4, 6, 1, 1, Color.GRAY));
            gridSquaresGameBoard[0][2].addWestEdgeBarrier();

            gridSquaresGameBoard[0][11].setBorder(BorderFactory.createMatteBorder(4, 1, 1, 6, Color.GRAY));
            gridSquaresGameBoard[0][11].addEastEdgeBarrier();
            gridSquaresGameBoard[0][12].setBorder(BorderFactory.createMatteBorder(4, 6, 1, 1, Color.GRAY));
            gridSquaresGameBoard[0][12].addWestEdgeBarrier();

            gridSquaresGameBoard[3][15].setBorder(BorderFactory.createMatteBorder(1, 1, 6, 4, Color.GRAY));
            gridSquaresGameBoard[3][15].addSouthEdgeBarrier();
            gridSquaresGameBoard[4][15].setBorder(BorderFactory.createMatteBorder(6, 1, 1, 4, Color.GRAY));
            gridSquaresGameBoard[4][15].addNorthEdgeBarrier();

            gridSquaresGameBoard[10][15].setBorder(BorderFactory.createMatteBorder(1, 1, 6, 4, Color.GRAY));
            gridSquaresGameBoard[10][15].addSouthEdgeBarrier();
            gridSquaresGameBoard[11][15].setBorder(BorderFactory.createMatteBorder(6, 1, 1, 4, Color.GRAY));
            gridSquaresGameBoard[11][15].addNorthEdgeBarrier();

            gridSquaresGameBoard[15][11].setBorder(BorderFactory.createMatteBorder(1, 1, 4, 6, Color.GRAY));
            gridSquaresGameBoard[15][11].addEastEdgeBarrier();
            gridSquaresGameBoard[15][12].setBorder(BorderFactory.createMatteBorder(1, 6, 4, 1, Color.GRAY));
            gridSquaresGameBoard[15][12].addWestEdgeBarrier();

            gridSquaresGameBoard[15][6].setBorder(BorderFactory.createMatteBorder(1, 1, 4, 6, Color.GRAY));
            gridSquaresGameBoard[15][6].addEastEdgeBarrier();
            gridSquaresGameBoard[15][7].setBorder(BorderFactory.createMatteBorder(1, 6, 4, 1, Color.GRAY));
            gridSquaresGameBoard[15][7].addWestEdgeBarrier();

            gridSquaresGameBoard[5][0].setBorder(BorderFactory.createMatteBorder(1, 4, 6, 1, Color.GRAY));
            gridSquaresGameBoard[5][0].addSouthEdgeBarrier();
            gridSquaresGameBoard[6][0].setBorder(BorderFactory.createMatteBorder(6, 4, 1, 1, Color.GRAY));
            gridSquaresGameBoard[6][0].addNorthEdgeBarrier();

            gridSquaresGameBoard[11][0].setBorder(BorderFactory.createMatteBorder(1, 4, 6, 1, Color.GRAY));
            gridSquaresGameBoard[11][0].addSouthEdgeBarrier();
            gridSquaresGameBoard[12][0].setBorder(BorderFactory.createMatteBorder(6, 4, 1, 1, Color.GRAY));
            gridSquaresGameBoard[12][0].addNorthEdgeBarrier();

            //set the interior barriers and the logic
            //First do outer perimeter logic barriers
            //First do north edge of whole board can overwrite corners
            for (int i = 0; i < 16; i++) {
                gridSquaresGameBoard[0][i].addNorthEdgeBarrier();
            }
            //Then east edge of whole board can overwrite corners
            for (int i = 0; i < 16; i++) {
                gridSquaresGameBoard[i][15].addEastEdgeBarrier();
            }

            //Then south edge of whole board can overwrite corners
            for (int i = 0; i < 16; i++) {
                gridSquaresGameBoard[15][i].addSouthEdgeBarrier();
            }
            //Then west edge of whole board can overwrite corners
            for (int i = 0; i < 16; i++) {
                gridSquaresGameBoard[i][0].addWestEdgeBarrier();
            }

            //Now do rest
            //First corner barrier
            gridSquaresGameBoard[0][4].setBorder(BorderFactory.createMatteBorder(4, 1, 6, 1, Color.GRAY));
            gridSquaresGameBoard[0][4].addSouthEdgeBarrier();
            gridSquaresGameBoard[1][3].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 6, Color.GRAY));
            gridSquaresGameBoard[1][3].addEastEdgeBarrier();
            gridSquaresGameBoard[1][4].setBorder(BorderFactory.createMatteBorder(6, 6, 1, 1, Color.GRAY));
            gridSquaresGameBoard[1][4].addNorthEdgeBarrier();
            //Border not filled in.
            gridSquaresGameBoard[1][4].addWestEdgeBarrier(); //TODO what is this?

            //Second corner barrier
            gridSquaresGameBoard[0][13].setBorder(BorderFactory.createMatteBorder(4, 1, 6, 1, Color.GRAY));
            gridSquaresGameBoard[0][13].addSouthEdgeBarrier();
            gridSquaresGameBoard[1][12].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 6, Color.GRAY));
            gridSquaresGameBoard[1][12].addEastEdgeBarrier();
            gridSquaresGameBoard[1][13].setBorder(BorderFactory.createMatteBorder(6, 6, 1, 1, Color.GRAY));
            gridSquaresGameBoard[1][13].addNorthEdgeBarrier();
            gridSquaresGameBoard[1][13].addWestEdgeBarrier();

            //Third corner barrier
            gridSquaresGameBoard[13][3].setBorder(BorderFactory.createMatteBorder(1, 1, 6, 1, Color.GRAY));
            gridSquaresGameBoard[13][3].addSouthEdgeBarrier();
            gridSquaresGameBoard[14][2].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 6, Color.GRAY));
            gridSquaresGameBoard[14][2].addEastEdgeBarrier();
            gridSquaresGameBoard[14][3].setBorder(BorderFactory.createMatteBorder(6, 6, 1, 1, Color.GRAY));
            gridSquaresGameBoard[14][3].addNorthEdgeBarrier();
            gridSquaresGameBoard[14][3].addWestEdgeBarrier();

            //Fourth corner barrier
            gridSquaresGameBoard[12][10].setBorder(BorderFactory.createMatteBorder(1, 1, 6, 1, Color.GRAY));
            gridSquaresGameBoard[12][10].addSouthEdgeBarrier();
            gridSquaresGameBoard[13][9].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 6, Color.GRAY));
            gridSquaresGameBoard[13][9].addEastEdgeBarrier();
            gridSquaresGameBoard[13][10].setBorder(BorderFactory.createMatteBorder(6, 6, 1, 1, Color.GRAY));
            gridSquaresGameBoard[13][10].addNorthEdgeBarrier();
            gridSquaresGameBoard[13][10].addWestEdgeBarrier();

            //Fifth corner barrier
            gridSquaresGameBoard[2][9].setBorder(BorderFactory.createMatteBorder(1, 1, 6, 6, Color.GRAY));
            gridSquaresGameBoard[2][9].addSouthEdgeBarrier();
            gridSquaresGameBoard[2][9].addEastEdgeBarrier();
            gridSquaresGameBoard[2][10].setBorder(BorderFactory.createMatteBorder(1, 6, 1, 1, Color.GRAY));
            gridSquaresGameBoard[2][10].addWestEdgeBarrier();
            gridSquaresGameBoard[3][9].setBorder(BorderFactory.createMatteBorder(6, 1, 1, 1, Color.GRAY));
            gridSquaresGameBoard[3][9].addNorthEdgeBarrier();

            //Sixth corner barrier
            gridSquaresGameBoard[3][6].setBorder(BorderFactory.createMatteBorder(1, 1, 6, 6, Color.GRAY));
            gridSquaresGameBoard[3][6].addSouthEdgeBarrier();
            gridSquaresGameBoard[3][6].addEastEdgeBarrier();
            gridSquaresGameBoard[3][7].setBorder(BorderFactory.createMatteBorder(1, 6, 1, 1, Color.GRAY));
            gridSquaresGameBoard[3][7].addWestEdgeBarrier();
            gridSquaresGameBoard[4][6].setBorder(BorderFactory.createMatteBorder(6, 1, 1, 1, Color.GRAY));
            gridSquaresGameBoard[4][6].addNorthEdgeBarrier();

            //Seventh corner barrier
            gridSquaresGameBoard[9][1].setBorder(BorderFactory.createMatteBorder(1, 1, 6, 6, Color.GRAY));
            gridSquaresGameBoard[9][1].addSouthEdgeBarrier();
            gridSquaresGameBoard[9][1].addEastEdgeBarrier();
            gridSquaresGameBoard[9][2].setBorder(BorderFactory.createMatteBorder(1, 6, 1, 1, Color.GRAY));
            gridSquaresGameBoard[9][2].addWestEdgeBarrier();
            gridSquaresGameBoard[10][1].setBorder(BorderFactory.createMatteBorder(6, 1, 1, 1, Color.GRAY));
            gridSquaresGameBoard[10][1].addNorthEdgeBarrier();


            //Eighth corner barrier
            gridSquaresGameBoard[9][14].setBorder(BorderFactory.createMatteBorder(1, 1, 6, 6, Color.GRAY));
            gridSquaresGameBoard[9][14].addSouthEdgeBarrier();
            gridSquaresGameBoard[9][14].addEastEdgeBarrier();
            gridSquaresGameBoard[9][15].setBorder(BorderFactory.createMatteBorder(1, 6, 1, 4, Color.GRAY));
            gridSquaresGameBoard[9][15].addWestEdgeBarrier();
            gridSquaresGameBoard[10][14].setBorder(BorderFactory.createMatteBorder(6, 1, 1, 1, Color.GRAY));
            gridSquaresGameBoard[10][14].addNorthEdgeBarrier();

            //Ninth corner barrier
            gridSquaresGameBoard[1][1].setBorder(BorderFactory.createMatteBorder(1, 1, 6, 1, Color.GRAY));
            gridSquaresGameBoard[1][1].addSouthEdgeBarrier();
            gridSquaresGameBoard[2][1].setBorder(BorderFactory.createMatteBorder(6, 1, 1, 6, Color.GRAY));
            gridSquaresGameBoard[2][1].addNorthEdgeBarrier();
            gridSquaresGameBoard[2][1].addWestEdgeBarrier();
            gridSquaresGameBoard[2][2].setBorder(BorderFactory.createMatteBorder(1, 6, 1, 1, Color.GRAY));
            gridSquaresGameBoard[2][2].addEastEdgeBarrier();

            //Tenth corner barrier
            gridSquaresGameBoard[7][5].setBorder(BorderFactory.createMatteBorder(1, 1, 6, 1, Color.GRAY));
            gridSquaresGameBoard[7][5].addSouthEdgeBarrier();
            gridSquaresGameBoard[8][5].setBorder(BorderFactory.createMatteBorder(6, 1, 1, 6, Color.GRAY));
            gridSquaresGameBoard[8][5].addNorthEdgeBarrier();
            gridSquaresGameBoard[8][5].addEastEdgeBarrier();
            gridSquaresGameBoard[8][6].setBorder(BorderFactory.createMatteBorder(1, 6, 1, 1, Color.GRAY));
            gridSquaresGameBoard[8][6].addWestEdgeBarrier();

            //Eleventh corner barrier
            gridSquaresGameBoard[5][11].setBorder(BorderFactory.createMatteBorder(1, 1, 6, 1, Color.GRAY));
            gridSquaresGameBoard[5][11].addSouthEdgeBarrier();
            gridSquaresGameBoard[6][11].setBorder(BorderFactory.createMatteBorder(6, 1, 1, 6, Color.GRAY));
            gridSquaresGameBoard[6][11].addNorthEdgeBarrier();
            gridSquaresGameBoard[6][11].addEastEdgeBarrier();
            gridSquaresGameBoard[6][12].setBorder(BorderFactory.createMatteBorder(1, 6, 1, 1, Color.GRAY));
            gridSquaresGameBoard[6][12].addWestEdgeBarrier();

            //Tweleth corner barrier
            gridSquaresGameBoard[9][8].setBorder(BorderFactory.createMatteBorder(1, 1, 6, 1, Color.GRAY));
            gridSquaresGameBoard[9][8].addSouthEdgeBarrier();
            gridSquaresGameBoard[10][8].setBorder(BorderFactory.createMatteBorder(6, 1, 1, 6, Color.GRAY));
            gridSquaresGameBoard[10][8].addNorthEdgeBarrier();
            gridSquaresGameBoard[10][8].addEastEdgeBarrier();
            gridSquaresGameBoard[10][9].setBorder(BorderFactory.createMatteBorder(1, 6, 1, 1, Color.GRAY));
            gridSquaresGameBoard[10][9].addWestEdgeBarrier();

            //Thirteenth corner barrier
            gridSquaresGameBoard[12][5].setBorder(BorderFactory.createMatteBorder(1, 1, 6, 1, Color.GRAY));
            gridSquaresGameBoard[12][5].addSouthEdgeBarrier();
            gridSquaresGameBoard[13][5].setBorder(BorderFactory.createMatteBorder(6, 1, 1, 6, Color.GRAY));
            gridSquaresGameBoard[13][5].addNorthEdgeBarrier();
            gridSquaresGameBoard[13][5].addEastEdgeBarrier();
            gridSquaresGameBoard[13][6].setBorder(BorderFactory.createMatteBorder(1, 6, 1, 1, Color.GRAY));
            gridSquaresGameBoard[13][6].addWestEdgeBarrier();

            //Fourteenth corner barrier
            gridSquaresGameBoard[6][2].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 6, Color.GRAY));
            gridSquaresGameBoard[6][2].addEastEdgeBarrier();
            gridSquaresGameBoard[6][3].setBorder(BorderFactory.createMatteBorder(1, 6, 6, 1, Color.GRAY));
            gridSquaresGameBoard[6][3].addWestEdgeBarrier();
            gridSquaresGameBoard[6][3].addSouthEdgeBarrier();
            gridSquaresGameBoard[7][3].setBorder(BorderFactory.createMatteBorder(6, 1, 1, 1, Color.GRAY));
            gridSquaresGameBoard[7][3].addNorthEdgeBarrier();

            //Fifteenth corner barrier
            gridSquaresGameBoard[5][13].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 6, Color.GRAY));
            gridSquaresGameBoard[5][13].addEastEdgeBarrier();
            gridSquaresGameBoard[5][14].setBorder(BorderFactory.createMatteBorder(1, 6, 6, 1, Color.GRAY));
            gridSquaresGameBoard[5][14].addWestEdgeBarrier();
            gridSquaresGameBoard[5][14].addSouthEdgeBarrier();
            gridSquaresGameBoard[6][14].setBorder(BorderFactory.createMatteBorder(6, 1, 1, 1, Color.GRAY));
            gridSquaresGameBoard[6][14].addNorthEdgeBarrier();

            //Sixteenth corner barrier
            gridSquaresGameBoard[10][3].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 6, Color.GRAY));
            gridSquaresGameBoard[10][3].addEastEdgeBarrier();
            gridSquaresGameBoard[10][4].setBorder(BorderFactory.createMatteBorder(1, 6, 6, 1, Color.GRAY));
            gridSquaresGameBoard[10][4].addWestEdgeBarrier();
            gridSquaresGameBoard[10][4].addSouthEdgeBarrier();
            gridSquaresGameBoard[11][4].setBorder(BorderFactory.createMatteBorder(6, 1, 1, 1, Color.GRAY));
            gridSquaresGameBoard[11][4].addNorthEdgeBarrier();

            //Barrier
            gridSquaresGameBoard[11][12].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 6, Color.GRAY));
            gridSquaresGameBoard[11][12].addEastEdgeBarrier();
            gridSquaresGameBoard[11][13].setBorder(BorderFactory.createMatteBorder(1, 6, 6, 1, Color.GRAY));
            gridSquaresGameBoard[11][13].addWestEdgeBarrier();
            gridSquaresGameBoard[11][13].addSouthEdgeBarrier();
            gridSquaresGameBoard[12][13].setBorder(BorderFactory.createMatteBorder(6, 1, 1, 1, Color.GRAY));
            gridSquaresGameBoard[12][13].addNorthEdgeBarrier();

            //Barriers in center
            gridSquaresGameBoard[7][7].setBorder(BorderFactory.createMatteBorder(8, 8, 0, 0, Color.GRAY));
            gridSquaresGameBoard[7][7].addNorthEdgeBarrier();
            gridSquaresGameBoard[6][7].addSouthEdgeBarrier();
            gridSquaresGameBoard[7][7].addWestEdgeBarrier();
            gridSquaresGameBoard[7][6].addEastEdgeBarrier();



            gridSquaresGameBoard[7][8].setBorder(BorderFactory.createMatteBorder(8, 0, 0, 8, Color.GRAY));
            gridSquaresGameBoard[7][8].addNorthEdgeBarrier();
            gridSquaresGameBoard[6][8].addSouthEdgeBarrier();
            gridSquaresGameBoard[7][8].addEastEdgeBarrier();
            gridSquaresGameBoard[7][9].addWestEdgeBarrier();


            gridSquaresGameBoard[8][7].setBorder(BorderFactory.createMatteBorder(0, 8, 8, 0, Color.GRAY));
            gridSquaresGameBoard[8][7].addWestEdgeBarrier();
            gridSquaresGameBoard[8][8].addWestEdgeBarrier();
            gridSquaresGameBoard[8][7].addSouthEdgeBarrier();
            gridSquaresGameBoard[9][7].addNorthEdgeBarrier();



            gridSquaresGameBoard[8][8].setBorder(BorderFactory.createMatteBorder(0, 0, 8, 8, Color.GRAY));
            gridSquaresGameBoard[8][8].addSouthEdgeBarrier();
            gridSquaresGameBoard[9][8].addNorthEdgeBarrier();
            gridSquaresGameBoard[8][8].addEastEdgeBarrier();
            gridSquaresGameBoard[8][9].addWestEdgeBarrier();

        }

        private void makeTargetChipsAndPutLatestOnBoard () {
            redCircleTargetChip = new TargetChip("RED", "CIRCLE", new ImageIcon(this.getClass().getResource("/redcircle.jpg")), 10, 9);
            redSquareTargetChip = new TargetChip("RED", "SQUARE", new ImageIcon(this.getClass().getResource("/redsquare.jpg")), 15, 6);
            redTriangleTargetChip = new TargetChip("RED", "TRIANGLE", new ImageIcon(this.getClass().getResource("/redtriangle.jpg")), 4, 15);
            redHexTargetChip = new TargetChip("RED", "HEX", new ImageIcon(this.getClass().getResource("/redhex.jpg")), 13, 5);

            blueCircleTargetChip = new TargetChip("BLUE", "CIRCLE", new ImageIcon(this.getClass().getResource("/bluecircle.jpg")), 9, 1);
            blueSquareTargetChip = new TargetChip("BLUE", "SQUARE", new ImageIcon(this.getClass().getResource("/bluesquare.jpg")), 6, 3);
            blueTriangleTargetChip = new TargetChip("BLUE", "TRIANGLE", new ImageIcon(this.getClass().getResource("/bluetriangle.jpg")), 2, 9);
            blueHexTargetChip = new TargetChip("BLUE", "HEX", new ImageIcon(this.getClass().getResource("/bluehex.jpg")), 13, 10);

            yellowCircleTargetChip = new TargetChip("YELLOW", "CIRCLE", new ImageIcon(this.getClass().getResource("/yellowcircle.jpg")), 6, 11);
            yellowSquareTargetChip = new TargetChip("YELLOW", "SQUARE", new ImageIcon(this.getClass().getResource("/yellowsquare.jpg")), 6, 11);
            yellowTriangleTargetChip = new TargetChip("YELLOW", "TRIANGLE", new ImageIcon(this.getClass().getResource("/yellowtriangle.jpg")), 14, 3);
            yellowHexTargetChip = new TargetChip("YELLOW", "HEX", new ImageIcon(this.getClass().getResource("/yellowhex.jpg")), 3, 6);

            greenCircleTargetChip = new TargetChip("GREEN", "CIRCLE", new ImageIcon(this.getClass().getResource("/greencircle.jpg")), 11, 13);
            greenSquareTargetChip = new TargetChip("GREEN", "SQUARE", new ImageIcon(this.getClass().getResource("/greensquare.jpg")), 10, 4);
            greenTriangleTargetChip = new TargetChip("GREEN", "TRIANGLE", new ImageIcon(this.getClass().getResource("/greentriangle.jpg")), 2, 1);
            greenHexTargetChip = new TargetChip("GREEN", "HEX", new ImageIcon(this.getClass().getResource("/greenhex.jpg")), 5, 14);

            vortexTargetChip = new TargetChip("MULTICOLOR", "VORTEX", new ImageIcon(this.getClass().getResource("/vortex.jpeg")), 15, 11);

            //Now, put all into an array.

            TargetChip[] firstArray = {redHexTargetChip, redSquareTargetChip, redTriangleTargetChip,  redCircleTargetChip, yellowCircleTargetChip,
                    vortexTargetChip, blueCircleTargetChip, blueHexTargetChip, blueSquareTargetChip, blueTriangleTargetChip,
                     yellowHexTargetChip, yellowSquareTargetChip, yellowTriangleTargetChip};

            this.randomTargetChipArray = firstArray;

            updateNewTargetChipOnBoardAndSquareCoordinates();
            // ADD method to reset all aspects of board (bids, bid order, timer)
        }

        private void createButtonsWithIconsAndTargetTilesAndAddToGrid () {
            // for the bottom panel:
            // create the buttons and add them to the grid
            gridSquaresGameBoard = new GridSquare[y][x];
            for (int row = 0; row < y; row++) {
                for (int column = 0; column < x; column++) {
                    gridSquaresGameBoard[row][column] = new GridSquare(row, column);

                    gridSquaresGameBoard[row][column].setSize(50, 50);
                    gridSquaresGameBoard[row][column].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));

                    gridSquaresGameBoard[row][column].setBackground(Color.LIGHT_GRAY);

                    gridSquaresGameBoard[row][column].setIcon(new ImageIcon(boardTileIcon.getImage().getScaledInstance(35, 35, java.awt.Image.SCALE_SMOOTH)));
                    gameboardPanel.add(gridSquaresGameBoard[row][column]); //Adding to GridLayout happens to each row after row is filled.
                }
            }
            for (int row = 1; row < y; row++) {
                gridSquaresGameBoard[0][row].setBorder(BorderFactory.createMatteBorder(4, 1, 1, 1, Color.GRAY));
            }
            for (int column = 1; column < y; column++) {
                gridSquaresGameBoard[column][0].setBorder(BorderFactory.createMatteBorder(1, 4, 1, 1, Color.GRAY));
            }
            for (int row = 1; row < y; row++) {
                gridSquaresGameBoard[15][row].setBorder(BorderFactory.createMatteBorder(1, 1, 4, 1, Color.GRAY));
            }
            for (int column = 1; column < y; column++) {
                gridSquaresGameBoard[column][15].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 4, Color.GRAY));
            }
            gridSquaresGameBoard[0][0].setBorder(BorderFactory.createMatteBorder(4, 4, 1, 1, Color.GRAY));
            gridSquaresGameBoard[0][15].setBorder(BorderFactory.createMatteBorder(4, 1, 1, 4, Color.GRAY));
            gridSquaresGameBoard[15][0].setBorder(BorderFactory.createMatteBorder(1, 4, 4, 1, Color.GRAY));
            gridSquaresGameBoard[15][15].setBorder(BorderFactory.createMatteBorder(1, 1, 4, 4, Color.GRAY));

            //make all the TargetTiles
            redCircleTargetTile = new TargetTile("RED", "CIRCLE");
            redSquareTargetTile = new TargetTile("RED", "SQUARE");
            redHexTargetTile = new TargetTile("RED", "HEX");
            redTriangleTargetTile = new TargetTile("RED", "TRIANGLE");

            greenCircleTargetTile = new TargetTile("GREEN", "CIRCLE");
            greenSquareTargetTile = new TargetTile("GREEN", "SQUARE");
            greenHexTargetTile = new TargetTile("GREEN", "HEX");
            greenTriangleTargetTile = new TargetTile("GREEN", "TRIANGLE");

            yellowCircleTargetTile = new TargetTile("YELLOW", "CIRCLE");
            yellowSquareTargetTile = new TargetTile("YELLOW", "SQUARE");
            yellowHexTargetTile = new TargetTile("YELLOW", "HEX");
            yellowTriangleTargetTile = new TargetTile("YELLOW", "TRIANGLE");

            blueCircleTargetTile = new TargetTile("BLUE", "CIRCLE");
            blueSquareTargetTile = new TargetTile("BLUE", "SQUARE");
            blueHexTargetTile = new TargetTile("BLUE", "HEX");
            blueTriangleTargetTile = new TargetTile("BLUE", "TRIANGLE");

            vortexTargetTile = new TargetTile("MULTICOLOR", "VORTEX");

            //set Target Tiles and their icons
            //gridSquaresGameBoard[1][4].setIcon(new ImageIcon(redCircleIcon.getImage().getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH)));
            //gridSquaresGameBoard[1][4].addTargetTileToSquare(redCircleTargetTile);
            gridSquaresGameBoard[10][9].setIcon(new ImageIcon(redCircleIcon.getImage().getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH)));
            gridSquaresGameBoard[10][9].addTargetTileToSquare(redCircleTargetTile);
            //gridSquaresGameBoard[1][13].setIcon(new ImageIcon(redSquareIcon.getImage().getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH)));
            //gridSquaresGameBoard[1][13].addTargetTileToSquare(redSquareTargetTile);
            gridSquaresGameBoard[15][6].setIcon(new ImageIcon(redSquareIcon.getImage().getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH)));
            gridSquaresGameBoard[15][6].addTargetTileToSquare(redSquareTargetTile);
            gridSquaresGameBoard[2][1].setIcon(new ImageIcon(greenTriangleIcon.getImage().getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH)));
            gridSquaresGameBoard[2][1].addTargetTileToSquare(greenTriangleTargetTile);
            gridSquaresGameBoard[2][9].setIcon(new ImageIcon(blueTriangleIcon.getImage().getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH)));
            gridSquaresGameBoard[2][9].addTargetTileToSquare(blueTriangleTargetTile);
            gridSquaresGameBoard[3][6].setIcon(new ImageIcon(yellowHexIcon.getImage().getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH)));
            gridSquaresGameBoard[3][6].addTargetTileToSquare(yellowHexTargetTile);
            gridSquaresGameBoard[5][14].setIcon(new ImageIcon(greenHexIcon.getImage().getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH)));
            gridSquaresGameBoard[5][14].addTargetTileToSquare(greenHexTargetTile);
            gridSquaresGameBoard[6][3].setIcon(new ImageIcon(blueSquareIcon.getImage().getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH)));
            gridSquaresGameBoard[6][3].addTargetTileToSquare(blueSquareTargetTile);
            gridSquaresGameBoard[6][11].setIcon(new ImageIcon(yellowCircleIcon.getImage().getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH)));
            gridSquaresGameBoard[6][11].addTargetTileToSquare(yellowCircleTargetTile);
            //gridSquaresGameBoard[8][5].setIcon(new ImageIcon(vortexIcon.getImage().getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH)));
            //gridSquaresGameBoard[8][5].addTargetTileToSquare(vortexTargetTile);
            gridSquaresGameBoard[15][11].setIcon(new ImageIcon(vortexIcon.getImage().getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH)));
            gridSquaresGameBoard[15][11].addTargetTileToSquare(vortexTargetTile);
            gridSquaresGameBoard[9][1].setIcon(new ImageIcon(blueCircleIcon.getImage().getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH)));
            gridSquaresGameBoard[9][1].addTargetTileToSquare(blueCircleTargetTile);
            gridSquaresGameBoard[9][14].setIcon(new ImageIcon(yellowSquareIcon.getImage().getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH)));
            gridSquaresGameBoard[9][14].addTargetTileToSquare(yellowSquareTargetTile);
            gridSquaresGameBoard[10][4].setIcon(new ImageIcon(greenSquareIcon.getImage().getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH)));
            gridSquaresGameBoard[10][4].addTargetTileToSquare(greenSquareTargetTile);
           // gridSquaresGameBoard[10][8].setIcon(new ImageIcon(redTriangleIcon.getImage().getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH)));
           // gridSquaresGameBoard[10][8].addTargetTileToSquare(redTriangleTargetTile);
            gridSquaresGameBoard[4][15].setIcon(new ImageIcon(redTriangleIcon.getImage().getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH)));
            gridSquaresGameBoard[4][15].addTargetTileToSquare(redTriangleTargetTile);
            gridSquaresGameBoard[11][13].setIcon(new ImageIcon(greenCircleIcon.getImage().getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH)));
            gridSquaresGameBoard[11][13].addTargetTileToSquare(greenCircleTargetTile);
            gridSquaresGameBoard[13][5].setIcon(new ImageIcon(redHexIcon.getImage().getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH)));
            gridSquaresGameBoard[13][5].addTargetTileToSquare(redHexTargetTile);
            gridSquaresGameBoard[13][10].setIcon(new ImageIcon(blueHexIcon.getImage().getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH)));
            gridSquaresGameBoard[13][10].addTargetTileToSquare(blueHexTargetTile);
            gridSquaresGameBoard[14][3].setIcon(new ImageIcon(yellowTriangleIcon.getImage().getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH)));
            gridSquaresGameBoard[14][3].addTargetTileToSquare(yellowTriangleTargetTile);
        }

        private void addRobotIconsAndRobotsToStartingPositions () {
            redRobotIcon = new ImageIcon(this.getClass().getResource("/redRobot.jpg"));
            greenRobotIcon = new ImageIcon(this.getClass().getResource("/greenRobot.jpg"));
            yellowRobotIcon = new ImageIcon(this.getClass().getResource("/yellowRobot.jpg"));
            blueRobotIcon = new ImageIcon(this.getClass().getResource("/blueRobot.jpg"));

            redRobot = new RobotPieces("RED", "STAR");
            redRobot.setRobotRowCoordinate(11);
            redRobot.setRobotColumnCoordinate(3);
            gridSquaresGameBoard[11][3].addRobotToSquare(redRobot);
            gridSquaresGameBoard[11][3].setIcon(new ImageIcon(redRobotIcon.getImage().getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH)));

            greenRobot = new RobotPieces("GREEN", "");
            greenRobot.setRobotRowCoordinate(5);
            greenRobot.setRobotColumnCoordinate(13);
            gridSquaresGameBoard[5][13].addRobotToSquare(greenRobot);
            gridSquaresGameBoard[5][13].setIcon(new ImageIcon(greenRobotIcon.getImage().getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH)));

            yellowRobot = new RobotPieces("YELLOW", "");
            yellowRobot.setRobotRowCoordinate(3);
            yellowRobot.setRobotColumnCoordinate(10);
            gridSquaresGameBoard[3][10].addRobotToSquare(yellowRobot);
            gridSquaresGameBoard[3][10].setIcon(new ImageIcon(yellowRobotIcon.getImage().getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH)));

            blueRobot = new RobotPieces("BLUE", "");
            blueRobot.setRobotRowCoordinate(0);
            blueRobot.setRobotColumnCoordinate(0);
            gridSquaresGameBoard[0][0].addRobotToSquare(blueRobot);
            gridSquaresGameBoard[0][0].setIcon(new ImageIcon(blueRobotIcon.getImage().getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH)));
        }

}