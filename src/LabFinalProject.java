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

    Item selectedItem = null;

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
        // centerPanel 안 subPanel: JTabbedPane과 추가 버튼
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

        subPanel.add(BorderLayout.CENTER, tabbedPane);
        JButton addButton = new JButton("추가");
        subPanel.add(BorderLayout.SOUTH, addButton);

        centerPanel.add(subPanel);

        // centerPanel 안 mainPanel: Item 정보 출력
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel infoPanel = new JPanel();
        mainPanel.setSize(650, 600);
        mainPanel.setLocation(250, 0);

        infoPanel.setLayout(null);
        // 테두리와 제목, 위치와 크기 설정
        Border infoBorder = BorderFactory.createTitledBorder("상세 보기");
        infoPanel.setBorder(infoBorder);
        // 상세 정보 출력
        if (selectedItem instanceof Movie) {
            String[] titles = {"제목", "감독", "배우", "장르", "등급", "개봉년도", "별점"};
            for (String s: titles) {

            }
        } else if (selectedItem instanceof Book) {
            String[] titles = {"제목", "저자", "출판사", "출판년도", "별점"};
            for (String s: titles) {

            }
        }

        // 줄거리
        JPanel plotPanel = new JPanel(new BorderLayout());
        Border plotBorder = BorderFactory.createTitledBorder("줄거리");
        plotPanel.setBorder(plotBorder);
        JTextArea plotArea = new JTextArea();
        plotPanel.add(BorderLayout.CENTER, plotArea);
        plotPanel.setSize(650, 140);
        plotPanel.setLocation(0, 280);

        // 감상평
        JPanel opinionPanel = new JPanel(new BorderLayout());
        Border opinionBorder = BorderFactory.createTitledBorder("감상평");
        opinionPanel.setBorder(opinionBorder);
        JTextArea opinionArea = new JTextArea();
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

    public static void main(String[] args) {
        new LabFinalProject();
    }
}
