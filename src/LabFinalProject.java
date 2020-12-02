import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class LabFinalProject extends JFrame {
    Container c = getContentPane();

    ItemCollections movieCollections = new ItemCollections();
    ItemCollections bookCollections = new ItemCollections();

    Vector<String> movieTitles = movieCollections.getTitles();
    Vector<String> bookTitles = bookCollections.getTitles();
    Vector<String> allTitles = new Vector<>();

    SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 hh:mm:ss");
    String formattedTime = format.format(System.currentTimeMillis());
    JLabel timeLabel = new JLabel(formattedTime);
    // 현재 선택된 아이템
    Item selectedItem = null;
    // 포스터와 표지 정보 JLabel
    JLabel imageLabel = new JLabel();
    // 각각 영화/책 정보를 담을 JLabel 배열
    JLabel[] movieInfoLabels = new JLabel[7];
    JLabel[] bookInfoLabels = new JLabel[5];
    // 각각 영화/책의 세부 정보
    String[] movieInfos = {"제목", "감독", "배우", "장르", "등급", "개봉년도", "포스터", "별점", "줄거리", "감상평"};
    String[] bookInfos = {"제목", "저자", "출판사", "출판년도", "책이미지", "별점", "줄거리", "감상평"};

    // 줄거리와 감상평 JTextArea
    JTextArea plotArea = new JTextArea();
    JTextArea opinionArea = new JTextArea();

    JPanel dialogMainPanel = new JPanel();

    public LabFinalProject() {
        // 타이틀 설정
        setTitle("JAVA 002 1914266 남수연");
        // x 버튼을 누르면 프로그램이 종료되도록 함
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        c.setLayout(new BorderLayout());
        // 메뉴 설정
        createMenu();

        // NORTH - upperPanel: 제목과 현재 시간
        JPanel upperPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("My Notes");
        titleLabel.setFont(new Font(titleLabel.getFont().getFontName(), Font.BOLD + Font.ITALIC, 25));
        titleLabel.setForeground(Color.BLUE);

        TimerThread timerThread = new TimerThread();
        timerThread.start();

        upperPanel.add(BorderLayout.WEST, titleLabel);
        upperPanel.add(BorderLayout.EAST, timeLabel);
        c.add(BorderLayout.NORTH, upperPanel);


        // CENTER - centerPanel: 전체/영화/도서/검색 + 추가 버튼
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(null);

        // * centerPanel 안 subPanel: JTabbedPane과 추가 버튼
        JPanel subPanel = new JPanel(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();
        subPanel.setSize(250, 600);
        subPanel.setLocation(0, 0);

        allTitles.addAll(movieTitles);
        allTitles.addAll(bookTitles);

        JList<String> allTab = new JList<String>(allTitles);
        tabbedPane.add("전체", allTab);
        JList<String> movieTab = new JList<String>(movieTitles);
        tabbedPane.add("영화", movieTab);
        JList<String> bookTab = new JList<String>(bookTitles);
        tabbedPane.add("도서", bookTab);
        JList<String> searchTab = new JList<String>(allTitles);
        tabbedPane.add("검색", searchTab);

        // 버튼 가운데정렬을 위해 패널 생성
        // TODO: 추가 버튼 눌렀을 때 입력 다이얼로그 팝업
        JPanel bottomPanel = new JPanel();
        JButton addButton = new JButton("추가");
        AddDialog addDialog = new AddDialog(this, "입력");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: 내용 작성
                addDialog.setVisible(true);
            }
        });
        bottomPanel.add(addButton);

        subPanel.add(BorderLayout.CENTER, tabbedPane);
        subPanel.add(BorderLayout.SOUTH, bottomPanel);
        centerPanel.add(subPanel);

        // * centerPanel 안 mainPanel: Item 정보 출력
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel infoPanel = new JPanel();
        mainPanel.setSize(650, 600);
        mainPanel.setLocation(250, 0);

        // mainPanel 안 infoPanel: 자유배치, 영화나 도서에 대한 상세 정보 출력
        infoPanel.setLayout(null);
        // 테두리와 제목, 위치와 크기 설정
        Border infoBorder = BorderFactory.createTitledBorder("상세 보기");
        infoPanel.setBorder(infoBorder);
        // 상세 정보 출력

        imageLabel.setSize(150, 250);
        imageLabel.setLocation(0, 10);



        // TODO: 이 부분은 이벤트리스너에 넣는게 맞는듯
        if (selectedItem instanceof Movie) {


            int gap = 20;
            for (String s: movieInfos) {

            }
        } else if (selectedItem instanceof Book) {


            int gap = 30;
            for (String s: bookInfos) {

            }
        }

        // 줄거리
        JPanel plotPanel = new JPanel(new BorderLayout());
        Border plotBorder = BorderFactory.createTitledBorder("줄거리");
        plotPanel.setBorder(plotBorder);
        plotPanel.add(BorderLayout.CENTER, plotArea);
        plotPanel.setSize(650, 140);
        plotPanel.setLocation(0, 280);

        // 감상평
        JPanel opinionPanel = new JPanel(new BorderLayout());
        Border opinionBorder = BorderFactory.createTitledBorder("감상평");
        opinionPanel.setBorder(opinionBorder);
        opinionPanel.add(BorderLayout.CENTER, opinionArea);
        opinionPanel.setSize(650, 140);
        opinionPanel.setLocation(0, 420);

        // bottomButtonsPanel: 하단 수정, 삭제 버튼을 담을 용도
        JPanel bottomButtonsPanel = new JPanel();
        JButton modifyButton = new JButton("수정");
        JButton deleteButton = new JButton("삭제");

        bottomButtonsPanel.add(modifyButton);
        bottomButtonsPanel.add(deleteButton);

        infoPanel.add(plotPanel);
        infoPanel.add(opinionPanel);

        mainPanel.add(BorderLayout.CENTER, infoPanel);
        mainPanel.add(BorderLayout.SOUTH, bottomButtonsPanel);

        centerPanel.add(mainPanel);

        c.add(BorderLayout.CENTER, centerPanel);





        // 프레임 사이즈 설정
        setSize(900, 680);
        // 화면에 프레임 출력
        setVisible(true);
    }

    private void createMenu() { // 메뉴 생성
        JMenuBar bar = new JMenuBar();
        // 파일 탭
        JMenu fileMenu = new JMenu("파일");
        // 파일 탭 속 메뉴 아이템 배열 생성
        JMenuItem[] fileMenuItems = new JMenuItem[4];
        // 메뉴 아이템 타이틀 배열 생성, 3번째 원소는 반복문 안에서 구분선을 생성하기 위한 더미 데이터
        String[] fileItemTitles = {"불러오기", "저장하기", "", "종료"};
        // 메뉴 이벤트 리스너 객체 생성
        MenuActionListener listener = new MenuActionListener();
        for (int i = 0; i < fileItemTitles.length; i++) {
            if (i == 2) {
                fileMenu.addSeparator();
                continue;
            }
            // 파일 메뉴 아이템 객체 생성 후 리스너 추가, 파일 탭에 추가
            fileMenuItems[i] = new JMenuItem(fileItemTitles[i]);
            fileMenuItems[i].addActionListener(listener);
            fileMenu.add(fileMenuItems[i]);
        }
        // 도움말 탭
        JMenu helpMenu = new JMenu("도움말");
        // 도움말 메뉴 아이템 객체 생성 후 리스너 추가, 편집 탭에 추가
        JMenuItem helpMenuItem = new JMenuItem("시스템 정보");
        helpMenuItem.addActionListener(listener);
        helpMenu.add(helpMenuItem);
        // 메뉴 바에 각 탭 추가
        bar.add(fileMenu);
        bar.add(helpMenu);

        setJMenuBar(bar);
    }

    // 상단바 메뉴 이벤트 리스너
    class MenuActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();
            // 파일 다이얼로그 생성
            JFileChooser chooser = new JFileChooser();
            switch (cmd) {
                case "불러오기": // 파일 열기 다이얼로그 이용
                    int ret1 = chooser.showOpenDialog(null);
                    String filePath1 = chooser.getSelectedFile().getPath();
                    break;
                case "저장하기": // 파일 저장 다이얼로그 이용
                    int ret2 = chooser.showSaveDialog(null);
                    String filePath2 = chooser.getSelectedFile().getPath();
                    break;
                case "종료": // 컨펌 다이얼로그 이용
                    int result = JOptionPane.showConfirmDialog(null, "종료하시겠습니까?", "종료 확인", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) { // 예 선택 시 프로그램 종료
                        System.exit(0);
                    }
                    break;
                case "시스템 정보": // 버전 정보 다이얼로그 띄우기
                    JOptionPane.showMessageDialog(null, "MyNotes 시스템 v 1.0입니다.", "Message", JOptionPane.INFORMATION_MESSAGE);
                    break;
            }
        }
    }

    // 현재 시간 표시하기 - Thread 이용
    class TimerThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
                formattedTime = format.format(System.currentTimeMillis());
                timeLabel.setText(formattedTime);
            }
        }
    }

    // 추가 버튼 클릭시 팝업될 다이얼로그
    // TODO: Dialog
    class AddDialog extends JDialog {
        public AddDialog(JFrame frame, String title) {
            super(frame, title);
            this.setLayout(new BorderLayout());
            // 버튼 그룹 생성
            ButtonGroup buttonGroup = new ButtonGroup();
            // RadioButton을 담을 패널 생성
            JPanel radioButtonPanel = new JPanel();
            // Movie, Book RadioButton 생성, 버튼 그룹에 추가
            String[] text = {"Movie", "Book"};
            JRadioButton[] radioButtons = new JRadioButton[2];

            // 메인 패널 생성
            dialogMainPanel.setLayout(null);
            dialogMainPanel.setBorder(BorderFactory.createTitledBorder("영화 정보"));

            Rectangle[] bounds = {
                    new Rectangle(80, 35, 230, 35),
                    new Rectangle(80, 75, 230, 35),
                    new Rectangle(80, 115, 230, 35),
                    new Rectangle(80, 155, 230, 35),
                    new Rectangle(80, 195, 230, 35),
                    new Rectangle(80, 235, 230, 35),
                    new Rectangle(80, 275, 230, 35),
                    new Rectangle(80, 315, 230, 35),

                    new Rectangle(80, 365, 230, 75), // Movie 줄거리 위치
                    new Rectangle(80, 445, 230, 75), // Movie 감상평 위치
                    new Rectangle(80, 285, 230, 75), // Book 줄거리 위치
                    new Rectangle(80, 365, 230, 75)  // Book 감상평 위치
            };

            Rectangle[] textAreaLabelBounds = {
                    new Rectangle(30, 280, 50, 15), // Movie 줄거리 라벨 위치
                    new Rectangle(30, 320, 50, 15), // Movie 감상평 라벨 위치
                    new Rectangle(30, 315, 50, 15), // Book 줄거리 라벨 위치
                    new Rectangle(30, 395, 50, 15)  // Book 감상평 라벨 위치
            };

            // 영화 탭에 라벨 추가
            JLabel[] labels = new JLabel[10];
            for (int j = 0; j < labels.length; j++) {
                labels[j] = new JLabel(movieInfos[j]);
                if (j >= labels.length - 2) {
                    labels[j].setBounds(30, 315 + 80 * (j - 7), 50, 15);
                    dialogMainPanel.add(labels[j]);
                    continue;
                }
                labels[j].setBounds(30, 40 * (j + 1), 50, 15);
                dialogMainPanel.add(labels[j]);
            }
            // 텍스트필드, 콤보박스 생성
            JTextField[] textFields = new JTextField[3];
            JComboBox<String>[] comboBoxes = new JComboBox[3];

            // textField 추가: 제목, 감독, 배우
            for(int j = 0; j < 3; j++) {
                textFields[j] = new JTextField();
                textFields[j].setBounds(bounds[j]);
                dialogMainPanel.add(textFields[j]);
            }
            // comboBox에 들어갈 내용들
            String[][] comboboxContents = {
                    {"드라마", "로맨스", "스릴러", "판타지", "범죄", "코미디", "애니메이션"}, // 장르
                    {"전체 관람가", "12세 관람가", "15세 관람가", "청소년 관람불가"},   // 등급
                    {"2020", "2019", "2018", "2017", "2016", "2015", "2014", "2013", "2012", "2011"} // 개봉년도
            };
            // comboBox 추가: 장르, 등급, 개봉년도
            for (int j = 3; j < 6; j++) {
                comboBoxes[j - 3] = new JComboBox<String>(comboboxContents[j - 3]);
                comboBoxes[j - 3].setBounds(bounds[j]);
                dialogMainPanel.add(comboBoxes[j - 3]);
            }

            // 이미지 불러오기
            JPanel imagePanel = new JPanel();
            JTextField imagePath = new JTextField(10);
            imagePath.setEnabled(false);
            JButton getImageButton = new JButton("불러오기");
            imagePanel.add(imagePath);
            imagePanel.add(getImageButton);
            imagePanel.setBounds(bounds[6]);
            dialogMainPanel.add(imagePanel);

            // 별점
            JSlider slider = new JSlider(JSlider.HORIZONTAL, 1, 10, 5);
            slider.setPaintLabels(true);
            slider.setPaintTicks(true);
            slider.setPaintTrack(true);
            slider.setMajorTickSpacing(1);
            slider.setBounds(bounds[7]);
            dialogMainPanel.add(slider);

            // textArea 추가: 줄거리와 감상평
            JTextArea[] textAreas = new JTextArea[2];
            JScrollPane[] scrollTextAreas = new JScrollPane[2];
            for (int j = 0; j < 2; j++) {
                textAreas[j] = new JTextArea(5, 10);
                scrollTextAreas[j] = new JScrollPane(textAreas[j]);
                scrollTextAreas[j].setBounds(bounds[8 + j]);
                dialogMainPanel.add(scrollTextAreas[j]);
            }

            // 모든 입력용 컴포넌트를 보관, 관리하는 allComponents 배열
            JComponent[] allComponents = {
                textFields[0], textFields[1], textFields[2],
                comboBoxes[0], comboBoxes[1], comboBoxes[2],
                imagePanel,
                slider,
                scrollTextAreas[0], scrollTextAreas[1]
            };

            dialogMainPanel.setBorder(BorderFactory.createTitledBorder("영화 정보"));

            // RadioButton에 이벤트 등록
            for (int i = 0; i < 2; i++) {
                if (i == 0)
                    radioButtons[i] = new JRadioButton(text[i], true);
                else
                    radioButtons[i] = new JRadioButton(text[i]);
                buttonGroup.add(radioButtons[i]);
                radioButtonPanel.add(radioButtons[i]);
                // radioButton에 이벤트리스너 추가
                radioButtons[i].addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        if (e.getStateChange() == ItemEvent.DESELECTED) return;
                        if (radioButtons[0].isSelected()) { // Movie
                            // movie용 Label 추가
                            for (int i = 0; i < movieInfos.length; i++) {
                                labels[i].setText(movieInfos[i]);
                                labels[i].setVisible(true);
                            }
                            for (int i = 0; i < 8; i++) {
                                allComponents[i].setVisible(true);
                                allComponents[i].setBounds(bounds[i]);
                            }
                            // 줄거리, 감상평 위치 조절
                            for (int i = 0; i < 2; i++) {
                                labels[bookInfos.length - 2 + i].setBounds(textAreaLabelBounds[i]);
                                scrollTextAreas[i].setBounds(bounds[8 + i]);
                            }
                            dialogMainPanel.setBorder(BorderFactory.createTitledBorder("영화 정보"));
                        } else { // Book
                            for (int i = 0; i < bookInfos.length + 2; i++) {
                                if (i > bookInfos.length - 1) {
                                    labels[i].setVisible(false);
                                    continue;
                                } else if (i >= bookInfos.length - 2) {
                                    labels[i].setBounds(textAreaLabelBounds[i - bookInfos.length + 4]);
                                }
                                labels[i].setText(bookInfos[i]);
                            }
                            comboBoxes[2].setBounds(bounds[3]);
                            imagePanel.setBounds(bounds[4]);
                            slider.setBounds(bounds[5]);

                            comboBoxes[0].setVisible(false);
                            comboBoxes[1].setVisible(false);

                            scrollTextAreas[0].setBounds(bounds[10]);
                            scrollTextAreas[1].setBounds(bounds[11]);

                            // TODO: isVisible()로 가시성 체크하고 참이면 배열에 집어넣으셈 -> 생성된 배열 정보로 객체 생성 후 저장!

                            dialogMainPanel.setBorder(BorderFactory.createTitledBorder("도서 정보"));
                        }
                    }
                });
            }

            // 다이얼로그에 추가
            this.add(BorderLayout.NORTH, radioButtonPanel);

            this.add(BorderLayout.CENTER, dialogMainPanel);

            // 하단 버튼 패널
            JPanel buttonPanel = new JPanel();
            JButton okButton = new JButton("OK");
            buttonPanel.add(okButton);
            this.add(BorderLayout.SOUTH, buttonPanel);
            setSize(350, 650);
        }
    }

    public static void main(String[] args) {
        new LabFinalProject();
    }
}
