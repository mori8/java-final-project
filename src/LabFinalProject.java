import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class LabFinalProject extends JFrame {
    Container c = getContentPane();
    // allItemCollections: 아이템을 저장할 ItemCollections
    ItemCollections allItemCollections = new ItemCollections();
    // 날짜 포팻
    SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 hh:mm:ss");
    String formattedTime = format.format(System.currentTimeMillis());
    JLabel timeLabel = new JLabel(formattedTime);
    // 각 탭팬에 들어갈 JList
    JList<String> allTab = new JList<String>();
    JList<String> movieTab = new JList<String>();
    JList<String> bookTab = new JList<String>();
    JPanel searchPanel = new JPanel(new BorderLayout());
    String[] searchType = {"제목", "별점"};
    JList<String> searchTab = new JList<String>();
    // 검색할 키워드
    String keyword = null;
    int star = 0;
    // 검색 결과
    Vector<Item> searchItemResults = new Vector<>();
    Vector<String> searchTitleResults = new Vector<>();

    // * centerPanel 안 mainPanel: Item 정보 출력
    JPanel mainPanel = new JPanel(new BorderLayout());
    JPanel infoPanel = new JPanel();
    JPanel detailInfoPanel = new JPanel();
    JPanel movieInfoPanel = new JPanel();
    JPanel bookInfoPanel = new JPanel();

    // 현재 선택된 아이템
    int nowSelectedIndex = -1;
    Item nowSelectedItem = null;

    // 포스터와 표지 정보 JPanel
    ImagePanel imagePanel = new ImagePanel();
    // 각각 영화/책 정보를 담을 JLabel 배열
    JLabel[] movieInfoLabels = new JLabel[7]; // 제목, 감독, 배우 등...
    JLabel[] bookInfoLabels = new JLabel[5];  // 제목, 저자, 출판사 등...
    JLabel[] movieItemInfoLabels = new JLabel[7]; // 각 항목에 해당하는 아이템의 내용
    JLabel[] bookItemInfoLabels = new JLabel[5];

    // 각각 영화/책의 정보
    String[] movieInfos = {"제목", "감독", "배우", "장르", "등급", "개봉년도", "포스터", "별점", "줄거리", "감상평"};
    String[] bookInfos = {"제목", "저자", "출판사", "출판년도", "책이미지", "별점", "줄거리", "감상평"};

    String[] movieDetailInfos = {"제목", "감독", "배우", "장르", "등급", "개봉년도", "별점"};
    String[] bookDetailInfos = {"제목", "저자", "출판사", "출판년도", "별점"};

    // 줄거리와 감상평 JTextArea
    JTextArea plotArea = new JTextArea();
    JTextArea opinionArea = new JTextArea();

    JPanel movieMainPanel = new JPanel();
    JPanel bookMainPanel = new JPanel();

    // AddDialog에서 생성될 Item 객체의 타입
    int itemType = 0; // 0: Movie / 1: Book
    // 수정 모드, 수정 버튼 클릭 시 true, 다이얼로그 닫힐 때 다시 false
    boolean isEditingMode = false;

    // Movie, Book RadioButton 생성
    String[] text = {"Movie", "Book"};
    JRadioButton[] radioButtons = new JRadioButton[2];

    // comboBox에 들어갈 내용들
    String[][] comboboxContents = {
            {"드라마", "로맨스", "스릴러", "판타지", "범죄", "코미디", "애니메이션"}, // 장르
            {"전체 관람가", "12세 관람가", "15세 관람가", "청소년 관람불가"},   // 등급
            {"2020", "2019", "2018", "2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010", "2009", "2008", "2007", "2006", "2005", "2004", "2003", "2002", "2001", "2000"} // 개봉년도
    };

    // 다이얼로그에 들어갈 텍스트필드, 콤보박스
    JTextField[] mTextFields = new JTextField[3];
    JComboBox<String>[] mComboBoxes = new JComboBox[3];
    JTextField[] bTextFields = new JTextField[3];
    JComboBox<String> bComboBox = new JComboBox<String>(comboboxContents[2]);
    // 저장할 영화/책의 이미지 주소
    JTextField mImagePath = new JTextField(10);
    JTextField bImagePath = new JTextField(10);
    // 별점 슬라이더
    JSlider mSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 5);
    JSlider bSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 5);
    // textArea: 줄거리와 감상평
    JTextArea[] mTextAreas = new JTextArea[2];
    JTextArea[] bTextAreas = new JTextArea[2];

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
        // 타이머 스레드
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

        // tabbedPane에 메뉴 달기
        tabbedPane.add("전체", allTab);
        tabbedPane.add("영화", movieTab);
        tabbedPane.add("도서", bookTab);
        // 검색 탭
        searchPanel.add(BorderLayout.CENTER, searchTab);
        JPanel searchUIPanel = new JPanel();
        searchUIPanel.setLayout(new BorderLayout());
        JComboBox<String> searchComboBox = new JComboBox<>(searchType);
        JTextField searchKeyword = new JTextField(7);
        JButton searchButton = new JButton("검색");
        searchUIPanel.add(BorderLayout.WEST, searchComboBox);
        searchUIPanel.add(BorderLayout.CENTER, searchKeyword);
        searchUIPanel.add(BorderLayout.EAST, searchButton);
        searchPanel.add(BorderLayout.NORTH, searchUIPanel);
        tabbedPane.add("검색", searchPanel);

        // 검색 버튼 이벤트
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (searchComboBox.getSelectedIndex() == 0) { // 제목으로 검색
                    keyword = searchKeyword.getText();
                    searchItemResults = allItemCollections.findAllItemsByTitle(keyword);
                    searchTitleResults = allItemCollections.findAllByTitle(keyword);
                } else if (searchComboBox.getSelectedIndex() == 1) { // 별점으로 검색
                    star = Integer.parseInt(searchKeyword.getText());
                    searchItemResults = allItemCollections.findAllItemByStars(star);
                    searchTitleResults = allItemCollections.findAllByStars(star);
                }
                if (searchItemResults.size() == 0) { // 검색 결과가 없는 경우
                    JOptionPane.showMessageDialog(null, "[" + searchKeyword.getText() + "] 검색 결과가 없습니다.");
                    searchTab.setListData(new Vector<>());
                    return;
                }
                searchTab.setListData(searchTitleResults);
            }
        });

        // mainPanel 안 infoPanel: 자유배치, 영화나 도서에 대한 상세 정보 출력
        infoPanel.setLayout(null);
        // 테두리와 제목, 위치와 크기 설정
        Border infoBorder = BorderFactory.createTitledBorder("상세 보기");
        infoPanel.setBorder(infoBorder);
        // 영화를 선택했을 때 상세정보 UI
        int movieGap = 38;
        movieInfoPanel.setLayout(null);
        for (int i = 0; i < 7; i++) {
            movieInfoLabels[i] = new JLabel(movieDetailInfos[i]);
            movieInfoLabels[i].setBounds(0, 20 + movieGap * i, 50, 15);
            movieItemInfoLabels[i] = new JLabel();
            movieItemInfoLabels[i].setBounds(70, 20 + movieGap * i, 180, 15);
            movieInfoPanel.add(movieInfoLabels[i]);
            movieInfoPanel.add(movieItemInfoLabels[i]);
        }
        // 책을 선택했을 때의 상세정보 UI
        int bookGap = 55;
        bookInfoPanel.setLayout(null);
        for (int i = 0; i < 5; i++) {
            bookInfoLabels[i] = new JLabel(bookDetailInfos[i]);
            bookInfoLabels[i].setBounds(0, 20 + bookGap * i, 50, 15);
            bookItemInfoLabels[i] = new JLabel();
            bookItemInfoLabels[i].setBounds(70, 20 + bookGap * i, 180, 15);
            bookInfoPanel.add(bookInfoLabels[i]);
            bookInfoPanel.add(bookItemInfoLabels[i]);
        }

        detailInfoPanel.setLayout(new BorderLayout());
        detailInfoPanel.setBounds(210, 8, 350, 270);
        infoPanel.add(detailInfoPanel);

        // list에서 선택한 element의 내용이 하단 텍스트필드에 뜨도록 함
        allTab.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // 선택한 list element의 내용을 name에 저장, index는 nowSelectedIndex에 저장
                String name = (String) allTab.getSelectedValue();
                nowSelectedIndex = allTab.getSelectedIndex();
                if (nowSelectedIndex == -1) return;
                Item selectedItem = allItemCollections.findByIndex(nowSelectedIndex);
                if (selectedItem instanceof Movie) {
                    Movie movie = (Movie) allItemCollections.findMovie(name);
                    nowSelectedItem = movie;
                    movieView(movie);
                } else if (selectedItem instanceof Book) {
                    Book book = (Book) allItemCollections.findBook(name);
                    nowSelectedItem = book;
                    bookView(book);
                }
                infoPanel.revalidate();
                infoPanel.repaint();
            }
        });

        movieTab.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // 선택한 list element의 내용을 name에 저장, name을 통해 아이템컬렉션에서 영화 검색
                String name = movieTab.getSelectedValue();
                Movie movie = allItemCollections.findMovie(name);
                nowSelectedItem = movie;
                movieView(movie);
                infoPanel.revalidate();
                infoPanel.repaint();
            }
        });
        // 도서 탭
        bookTab.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // 선택한 list element의 내용을 name에 저장, name을 통해서 아이템컬렉션에서 도서 검색
                String name = bookTab.getSelectedValue();
                Book book = allItemCollections.findBook(name);
                nowSelectedItem = book;
                bookView(book);
                infoPanel.revalidate();
                infoPanel.repaint();
            }
        });
        // 검색 탭
        searchTab.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // 선택한 list element의 index를 nowSelectedIndex에 저장
                nowSelectedIndex = searchTab.getSelectedIndex();
                if (nowSelectedIndex == -1) return;
                nowSelectedItem = searchItemResults.elementAt(nowSelectedIndex);
                if (nowSelectedItem instanceof Movie) {
                    movieView((Movie) nowSelectedItem);
                } else if (nowSelectedItem instanceof Book) {
                    bookView((Book) nowSelectedItem);
                }
                infoPanel.revalidate();
                infoPanel.repaint();
            }
        });

        // 버튼 가운데정렬을 위해 패널 생성
        JPanel bottomPanel = new JPanel();
        JButton addButton = new JButton("추가");
        AddDialog addDialog = new AddDialog(this, "입력");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setMovieDialog(null);
                setBookDialog(null);
                addDialog.setVisible(true);
            }
        });
        bottomPanel.add(addButton);

        subPanel.add(BorderLayout.CENTER, tabbedPane);
        subPanel.add(BorderLayout.SOUTH, bottomPanel);
        centerPanel.add(subPanel);

        mainPanel.setSize(650, 600);
        mainPanel.setLocation(250, 0);

        imagePanel.setSize(180, 250);
        imagePanel.setLocation(20, 20);

        infoPanel.add(imagePanel);

        // 줄거리
        JPanel plotPanel = new JPanel(new BorderLayout());
        Border plotBorder = BorderFactory.createTitledBorder("줄거리");
        plotPanel.setBorder(plotBorder);
        plotArea.setLineWrap(true);
        plotPanel.add(BorderLayout.CENTER, plotArea);
        plotPanel.setSize(650, 140);
        plotPanel.setLocation(0, 280);

        // 감상평
        JPanel opinionPanel = new JPanel(new BorderLayout());
        Border opinionBorder = BorderFactory.createTitledBorder("감상평");
        opinionPanel.setBorder(opinionBorder);
        opinionArea.setLineWrap(true);
        opinionPanel.add(BorderLayout.CENTER, opinionArea);
        opinionPanel.setSize(650, 140);
        opinionPanel.setLocation(0, 420);

        // bottomButtonsPanel: 하단 수정, 삭제 버튼을 담을 용도
        JPanel bottomButtonsPanel = new JPanel();
        JButton modifyButton = new JButton("수정");
        JButton deleteButton = new JButton("삭제");
        // 수정
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nowSelectedItem instanceof Movie) {
                    setBookDialog(null);
                    setMovieDialog((Movie) nowSelectedItem);
                    radioButtons[0].setSelected(true);
                } else if (nowSelectedItem instanceof Book) {
                    setMovieDialog(null);
                    setBookDialog((Book) nowSelectedItem);
                    radioButtons[1].setSelected(true);
                }
                isEditingMode = true;
                addDialog.setVisible(true);
            }
        });
        // 삭제
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "정말 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) { // 예 선택 시 Item 삭제
                    allItemCollections.delete(nowSelectedItem);
                    if (nowSelectedItem instanceof Movie) {
                        movieTab.setListData(allItemCollections.getMovieTitles());
                        detailInfoPanel.remove(movieInfoPanel);
                    } else if (nowSelectedItem instanceof Book) {
                        bookTab.setListData(allItemCollections.getBookTitles());
                        detailInfoPanel.remove(bookInfoPanel);
                    }
                    allTab.setListData(allItemCollections.getTitles());
                    searchTab.setListData(new Vector<>());
                    nowSelectedItem = null;

                    imagePanel.setItemPath("");
                    plotArea.setText("");
                    opinionArea.setText("");

                    infoPanel.revalidate();
                    infoPanel.repaint();
                }
            }
        });

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
    private void movieView(Movie movie) {
        if (movie == null) return ;
        // 라벨 배치
        // "제목", "감독", "배우", "장르", "등급", "개봉년도", "별점"
        movieItemInfoLabels[0].setText(movie.getTitle());
        movieItemInfoLabels[1].setText(movie.getDirector());
        movieItemInfoLabels[2].setText(movie.getActors());
        movieItemInfoLabels[3].setText(comboboxContents[0][movie.getGenre()]);
        movieItemInfoLabels[4].setText(comboboxContents[1][movie.getGrade()]);
        movieItemInfoLabels[5].setText(comboboxContents[2][movie.getYear()]);
        movieItemInfoLabels[6].setText(movie.getStars() + "점");
        imagePanel.setItemPath(movie.getImagePath());
        plotArea.setText(movie.getPlot());
        opinionArea.setText(movie.getOpinion());
        detailInfoPanel.remove(bookInfoPanel);
        detailInfoPanel.add(BorderLayout.CENTER, movieInfoPanel);
    }

    private void bookView(Book book) {
        if (book == null) return ;
        // 라벨 배치
        // "제목", "저자", "출판사", "출판년도", "별점"
        bookItemInfoLabels[0].setText(book.getTitle());
        bookItemInfoLabels[1].setText(book.getAuthor());
        bookItemInfoLabels[2].setText(book.getPublisher());
        bookItemInfoLabels[3].setText(comboboxContents[2][book.getYear()]);
        bookItemInfoLabels[4].setText(book.getStars() + "점");
        imagePanel.setItemPath(book.getImagePath());
        plotArea.setText(book.getPlot());
        opinionArea.setText(book.getOpinion());
        detailInfoPanel.remove(movieInfoPanel);
        detailInfoPanel.add(BorderLayout.CENTER, bookInfoPanel);
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
                    if (ret1 != JFileChooser.APPROVE_OPTION) break;
                    String filePath1 = chooser.getSelectedFile().getPath();
                    try {
                        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath1));
                        allItemCollections = (ItemCollections) ois.readObject();
                        movieTab.setListData(allItemCollections.getMovieTitles());
                        bookTab.setListData(allItemCollections.getBookTitles());
                        allTab.setListData(allItemCollections.getTitles());
                        ois.close();
                    } catch (Exception exception) {
                        System.out.println(exception);
                    }
                    break;
                case "저장하기": // 파일 저장 다이얼로그 이용
                    int ret2 = chooser.showSaveDialog(null);
                    if (ret2 != JFileChooser.APPROVE_OPTION) break;
                    String filePath2 = chooser.getSelectedFile().getPath();
                    try {
                        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath2));
                        oos.writeObject(allItemCollections);
                        oos.close();
                    } catch (Exception exception) {
                        System.out.println(exception);
                    }
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
                    Thread.sleep(1000); // 1초마다 갱신
                } catch (InterruptedException e) {
                    return;
                }
                formattedTime = format.format(System.currentTimeMillis());
                timeLabel.setText(formattedTime);
            }
        }
    }

    // 추가 버튼 클릭시 팝업될 다이얼로그
    class AddDialog extends JDialog {
        public AddDialog(JFrame frame, String title) {
            super(frame, title);
            Container c = getContentPane();
            c.setLayout(new BorderLayout());
            // 버튼 그룹 생성
            ButtonGroup buttonGroup = new ButtonGroup();
            // RadioButton을 담을 패널 생성
            JPanel radioButtonPanel = new JPanel();

            // 메인 패널 생성
            movieMainPanel.setLayout(null);
            movieMainPanel.setBorder(BorderFactory.createTitledBorder("영화 정보"));

            bookMainPanel.setLayout(null);
            bookMainPanel.setBorder(BorderFactory.createTitledBorder("도서 정보"));

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
            JLabel[] mLabels = new JLabel[10];
            for (int j = 0; j < mLabels.length; j++) {
                mLabels[j] = new JLabel(movieInfos[j]);
                if (j >= mLabels.length - 2) {
                    mLabels[j].setBounds(30, 320 + 80 * (j - 7), 50, 15);
                    movieMainPanel.add(mLabels[j]);
                    continue;
                }
                mLabels[j].setBounds(30, 5 + 40 * (j + 1), 50, 15);
                movieMainPanel.add(mLabels[j]);
            }

            // 책 탭에 라벨 추가
            JLabel[] bLabels = new JLabel[8];
            for (int j = 0; j < bLabels.length; j++) {
                bLabels[j] = new JLabel(bookInfos[j]);
                if (j >= bLabels.length - 2) {
                    bLabels[j].setBounds(30, 240 + 80 * (j - 5), 50, 15);
                    bookMainPanel.add(bLabels[j]);
                    continue;
                }
                bLabels[j].setBounds(30, 5 + 40 * (j + 1), 50, 15);
                bookMainPanel.add(bLabels[j]);
            }

            // 각 탭에 textField 추가
            for(int j = 0; j < 3; j++) {
                mTextFields[j] = new JTextField();
                mTextFields[j].setBounds(bounds[j]);
                movieMainPanel.add(mTextFields[j]);

                bTextFields[j] = new JTextField();
                bTextFields[j].setBounds(bounds[j]);
                bookMainPanel.add(bTextFields[j]);
            }

            // 영화 탭에 comboBox 추가: 장르, 등급, 개봉년도
            for (int j = 3; j < 6; j++) {
                mComboBoxes[j - 3] = new JComboBox<String>(comboboxContents[j - 3]);
                mComboBoxes[j - 3].setBounds(bounds[j]);
                movieMainPanel.add(mComboBoxes[j - 3]);
            }
            // 책 탭에 comboBox 추가: 출판년도
            bComboBox.setBounds(bounds[3]);
            bookMainPanel.add(bComboBox);

            // 영화 이미지 불러오기
            JPanel mImagePanel = new JPanel();
            mImagePath.setEnabled(false);
            JButton getMovieImageButton = new JButton("불러오기");
            getMovieImageButton.addActionListener(new FileOpenActionListener());
            mImagePanel.add(mImagePath);
            mImagePanel.add(getMovieImageButton);
            mImagePanel.setBounds(bounds[6]);
            movieMainPanel.add(mImagePanel);

            // 책 이미지 불러오기
            JPanel bImagePanel = new JPanel();
            bImagePath.setEnabled(false);
            JButton getBookImageButton = new JButton("불러오기");
            getBookImageButton.addActionListener(new FileOpenActionListener());
            bImagePanel.add(bImagePath);
            bImagePanel.add(getBookImageButton);
            bImagePanel.setBounds(bounds[4]);
            bookMainPanel.add(bImagePanel);

            // 영화 별점
            mSlider.setPaintLabels(true);
            mSlider.setPaintTicks(true);
            mSlider.setPaintTrack(true);
            mSlider.setMajorTickSpacing(1);
            mSlider.setBounds(bounds[7]);
            movieMainPanel.add(mSlider);
            // 책 별점
            bSlider.setPaintLabels(true);
            bSlider.setPaintTicks(true);
            bSlider.setPaintTrack(true);
            bSlider.setMajorTickSpacing(1);
            bSlider.setBounds(bounds[5]);
            bookMainPanel.add(bSlider);

            JScrollPane[] mScrollTextAreas = new JScrollPane[2];
            for (int j = 0; j < 2; j++) {
                mTextAreas[j] = new JTextArea();
                mTextAreas[j].setLineWrap(true);
                mScrollTextAreas[j] = new JScrollPane(mTextAreas[j]);
                mScrollTextAreas[j].setBounds(bounds[8 + j]);
                movieMainPanel.add(mScrollTextAreas[j]);
            }

            JScrollPane[] bScrollTextAreas = new JScrollPane[2];
            for (int j = 0; j < 2; j++) {
                bTextAreas[j] = new JTextArea();
                bTextAreas[j].setLineWrap(true);
                bScrollTextAreas[j] = new JScrollPane(bTextAreas[j]);
                bScrollTextAreas[j].setBounds(bounds[10 + j]);
                bookMainPanel.add(bScrollTextAreas[j]);
            }

            // RadioButton에 이벤트 등록
            for (int i = 0; i < 2; i++) {
                if (i == 0) radioButtons[i] = new JRadioButton(text[i], true);
                else radioButtons[i] = new JRadioButton(text[i]);
                buttonGroup.add(radioButtons[i]);
                radioButtonPanel.add(radioButtons[i]);
                // radioButton에 이벤트리스너 추가
                radioButtons[i].addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        if (e.getStateChange() == ItemEvent.DESELECTED) ;
                        if (radioButtons[0].isSelected()) { // Movie
                            itemType = 0;
                            c.remove(bookMainPanel);
                            c.add(BorderLayout.CENTER, movieMainPanel);
                        } else { // Book
                            itemType = 1;
                            c.remove(movieMainPanel);
                            c.add(BorderLayout.CENTER, bookMainPanel);
                        }
                        c.revalidate();
                        c.repaint();
                    }
                });
            }

            // 다이얼로그에 추가
            this.add(BorderLayout.NORTH, radioButtonPanel);
            this.add(BorderLayout.CENTER, movieMainPanel);

            // 하단 버튼 패널
            JPanel buttonPanel = new JPanel();
            JButton okButton = new JButton("OK");
            okButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (itemType == 0) { // movie
                        int idx = 0;
                        String[] data = new String[10];

                        for (JTextField tf: mTextFields) data[idx++] = tf.getText();
                        for (JComboBox<String> c: mComboBoxes) data[idx++] = Integer.toString(c.getSelectedIndex());
                        data[idx++] = mImagePath.getText();
                        data[idx++] = Integer.toString(mSlider.getValue());
                        for (JTextArea ta: mTextAreas)  data[idx++] = ta.getText();

                        Movie movie = new Movie(
                                data[0], data[1], data[2], // 제목, 감독, 배우
                                Integer.parseInt(data[3]), Integer.parseInt(data[4]), Integer.parseInt(data[5]), // 장르, 등급, 개봉년도
                                data[6], Integer.parseInt(data[7]), // 포스터 저장경로, 별점
                                data[8], data[9] // 줄거리, 감상평
                        );
                        if (isEditingMode) {
                            allItemCollections.set(allItemCollections.indexOf(nowSelectedItem), movie);
                            nowSelectedItem = movie;
                            movieView(movie);
                        } else
                            allItemCollections.add(movie);
                        isEditingMode = false;
                        movieTab.setListData(allItemCollections.getMovieTitles());

                    } else if (itemType == 1) { // book
                        int idx = 0;
                        String[] data = new String[8];
                        for (JTextField tf: bTextFields) data[idx++] = tf.getText();
                        data[idx++] = Integer.toString(bComboBox.getSelectedIndex());
                        data[idx++] = bImagePath.getText();
                        data[idx++] = Integer.toString(bSlider.getValue());
                        for (JTextArea ta: bTextAreas) data[idx++] = ta.getText();

                        Book book = new Book(
                                data[0], data[1], data[2], // 제목, 감독, 배우
                                Integer.parseInt(data[3]), // 출판년도
                                data[4], Integer.parseInt(data[5]), // 포스터 저장경로, 별점
                                data[6], data[7] // 줄거리, 감상평
                        );
                        if (isEditingMode) {
                            allItemCollections.set(allItemCollections.indexOf(nowSelectedItem), book);
                            nowSelectedItem = book;
                            bookView(book);
                        } else
                            allItemCollections.add(book);
                        isEditingMode = false;
                        bookTab.setListData(allItemCollections.getBookTitles());
                    }
                    infoPanel.revalidate();
                    infoPanel.repaint();
                    allTab.setListData(allItemCollections.getTitles());
                    setVisible(false);
                }
            });
            buttonPanel.add(okButton);
            this.add(BorderLayout.SOUTH, buttonPanel);
            setSize(350, 650);
        }
    }

    public void setMovieDialog(Movie movie) {
        if (movie == null) { // 초기화
            for(int i = 0; i < 3; i++) mTextFields[i].setText("");
            for (int i = 0; i < 3; i++) mComboBoxes[i].setSelectedIndex(0);
            mSlider.setValue(5);
            mImagePath.setText("");
            for (int i = 0; i < 2; i++) mTextAreas[i].setText("");
            return;
        }
        mTextFields[0].setText(movie.getTitle());
        mTextFields[1].setText(movie.getDirector());
        mTextFields[2].setText(movie.getActors());
        mComboBoxes[0].setSelectedIndex(movie.getGenre());
        mComboBoxes[1].setSelectedIndex(movie.getGrade());
        mComboBoxes[2].setSelectedIndex(movie.getYear());
        mSlider.setValue(movie.getStars());
        mImagePath.setText(movie.getImagePath());
        mTextAreas[0].setText(movie.getPlot());
        mTextAreas[1].setText(movie.getOpinion());
    }

    public void setBookDialog(Book book) {
        if (book == null) { // 초기화
            for(int i = 0; i < 3; i++) bTextFields[i].setText("");
            bComboBox.setSelectedIndex(0);
            bSlider.setValue(5);
            bImagePath.setText("");
            for (int i = 0; i < 2; i++) bTextAreas[i].setText("");
            return;
        }
        bTextFields[0].setText(book.getTitle());
        bTextFields[1].setText(book.getAuthor());
        bTextFields[2].setText(book.getPublisher());
        bComboBox.setSelectedIndex(book.getYear());
        bSlider.setValue(book.getStars());
        bImagePath.setText(book.getImagePath());
        bTextAreas[0].setText(book.getPlot());
        bTextAreas[1].setText(book.getOpinion());
    }

    class FileOpenActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            int ret = chooser.showOpenDialog(null);
            if (ret != JFileChooser.APPROVE_OPTION) return;
            String filePath = chooser.getSelectedFile().getPath();
            if (itemType == 0) { // movie
                mImagePath.setText(filePath);
            } else { // book
                bImagePath.setText(filePath);
            }
        }
    }

    class ImagePanel extends JPanel {
        private String path;

        public void setItemPath(String path) {
            this.path = path;
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            ImageIcon imgIcon = new ImageIcon(path);
            Image img = imgIcon.getImage();
            g.setColor(Color.GRAY);
            g.drawLine(0, 0, 180, 250);
            g.drawLine(180, 0, 0, 250);
            g.drawString("이미지 없음", 62, 130);
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public static void main(String[] args) {
        new LabFinalProject();
    }
}
