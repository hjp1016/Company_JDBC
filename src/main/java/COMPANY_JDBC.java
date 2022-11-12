import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Vector;



public class COMPANY_JDBC extends JFrame implements ActionListener {

    public Connection conn;
    public Statement stmt;
    public ResultSet rs;

    private int Check_Column;

    private DefaultTableModel model;

    private Vector<String> EMPlist = new Vector<String>();


    private JTable table;
    private JPanel panel;
    private JLabel label;
    private JScrollPane scrollPane;

    private int NameCount;
    Container db = this;
    private JComboBox SR_Category;
    private JComboBox Dept1;
    private JComboBox Sex1;
    private JComboBox Dept2;
    private JComboBox Sex2;
    private JComboBox Month;
    private JComboBox Emp;
    private JComboBox UD_Category;

    private JCheckBox Name_ck = new JCheckBox("Name", true);
    private JCheckBox Ssn_ck = new JCheckBox("Ssn", true);
    private JCheckBox Bdate_ck = new JCheckBox("Bdate", true);
    private JCheckBox Add_ck = new JCheckBox("Address", true);
    private JCheckBox Sex_ck = new JCheckBox("Sex", true);
    private JCheckBox Sal_ck = new JCheckBox("Salary", true);
    private JCheckBox Sup_ck = new JCheckBox("Supervisor", true);
    private JCheckBox Dep_ck = new JCheckBox("Department", true);

    private JButton SR_Button = new JButton("검색");
    private JButton UD_Button = new JButton("UPDATE");
    private JButton DEL_Button = new JButton("선택한 데이터 삭제");
    private JButton ADD_Button = new JButton("데이터 추가");

    private JLabel SEL_EMP = new JLabel();
    private JLabel totalNum = new JLabel();

    private JTextField SR_FIELD = new JTextField(10);
    private JTextField UPDATE_FIELD = new JTextField(10);

    //정보 입력
    private JTextField ADD_Fn = new JTextField(20);
    private JTextField ADD_Mn = new JTextField(20);
    private JTextField ADD_Ln = new JTextField(20);
    private JTextField ADD_Ss = new JTextField(20);
    private JTextField ADD_Bd = new JTextField(20);
    private JTextField ADD_Ad = new JTextField(20);
    private JComboBox AddSex;
    private JTextField ADD_Sa = new JTextField(20);
    private JTextField ADD_Su = new JTextField(20);
    private JTextField ADD_Dn = new JTextField(20);


    private JButton ADDF_Button = new JButton("정보 추가하기");
    private JButton ADDC_Button = new JButton("취소하기");

    private JLabel ADDlabel = new JLabel("새로운 직원 정보 추가");




    private int check = 0;
    private int useField = 0;

    String namelist = "";

    public COMPANY_JDBC(){
        //검색창
        JPanel SEARCHPanel = new JPanel();
        String[] category = { "전체", "부서별", "성별", "연봉", "생일", "부하직원", "가족" };
        String[] dept = { "Research", "Administration", "Headquarters" };
        String[] sex = {"F", "M"};
        String[] month = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        String[] emp = {"Fname", "Minit", "Lname"};

        SR_Category = new JComboBox(category);
        Dept1 = new JComboBox(dept);
        Sex1 = new JComboBox(sex);
        Month = new JComboBox(month);
        Emp = new JComboBox(emp);


        Dept1.setVisible(false);
        Sex1.setVisible(false);
        SR_FIELD.setVisible(false);
        Month.setVisible(false);
        Emp.setVisible(false);

        SEARCHPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        SEARCHPanel.add(new JLabel("검색 범위 "));
        SEARCHPanel.add(SR_Category);
        SEARCHPanel.add(Dept1);
        SEARCHPanel.add(Sex1);
        SEARCHPanel.add(Emp);
        SEARCHPanel.add(SR_FIELD);
        SEARCHPanel.add(Month);

        //추가창
        JPanel ADDPanel = new JPanel();
        ADDPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        ADDPanel.add(ADD_Button);

        //체크창
        JPanel CheckBoxPanel = new JPanel();
        CheckBoxPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        CheckBoxPanel.add(new JLabel("검색 항목 "));
        CheckBoxPanel.add(Name_ck);
        CheckBoxPanel.add(Ssn_ck);
        CheckBoxPanel.add(Bdate_ck);
        CheckBoxPanel.add(Add_ck);
        CheckBoxPanel.add(Sex_ck);
        CheckBoxPanel.add(Sal_ck);
        CheckBoxPanel.add(Sup_ck);
        CheckBoxPanel.add(Dep_ck);
        CheckBoxPanel.add(SR_Button);

        //선택된 직원명단
        JPanel SelectedPanel = new JPanel();
        SelectedPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        SelectedPanel.add(new JLabel("선택된 직원 : "));
        SelectedPanel.add(SEL_EMP);

        //인원수
        JPanel TotalPanel = new JPanel();
        TotalPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        TotalPanel.add(new JLabel("인원수 : "));
        TotalPanel.add(totalNum);

        //수정창
        JPanel UpdatePanel = new JPanel();
        String[] UD_category = {"Address", "Sex", "Salary", "부서별 월급"};
        UD_Category = new JComboBox(UD_category);
        UpdatePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        ADDlabel.setFont(new Font("Dialog", Font.BOLD, 16));

        Dept2 = new JComboBox(dept);
        Sex2 = new JComboBox(sex);

        Sex2.setVisible(false);
        Dept2.setVisible(false);

        UpdatePanel.add(new JLabel("수정 : "));
        UpdatePanel.add(UD_Category);
        UpdatePanel.add(Sex2);
        UpdatePanel.add(Dept2);
        UpdatePanel.add(UPDATE_FIELD);
        UpdatePanel.add(UD_Button);

        //삭제창
        JPanel DeletePanel = new JPanel();
        DeletePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        DeletePanel.add(DEL_Button);



        JPanel Rs_Add = new JPanel();
        Rs_Add.setLayout(new BoxLayout(Rs_Add, BoxLayout.X_AXIS));
        Rs_Add.add(SEARCHPanel);
        Rs_Add.add(ADDPanel);

        JPanel Top = new JPanel();
        Top.setLayout(new BoxLayout(Top, BoxLayout.Y_AXIS));
        Top.add(Rs_Add);
        Top.add(CheckBoxPanel);

        JPanel Center = new JPanel();
        Center.setLayout(new BoxLayout(Center, BoxLayout.X_AXIS));
        Center.add(SelectedPanel);

        JPanel Bottom = new JPanel();
        Bottom.setLayout(new BoxLayout(Bottom, BoxLayout.X_AXIS));
        Bottom.add(TotalPanel);
        Bottom.add(UpdatePanel);
        Bottom.add(DeletePanel);

        JPanel Bot = new JPanel();
        Bot.setLayout(new BoxLayout(Bot, BoxLayout.Y_AXIS));
        Bot.add(Center);
        Bot.add(Bottom);

        add(Top, BorderLayout.NORTH);
        add(Bot, BorderLayout.SOUTH);

        SR_Category.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sr_list = SR_Category.getSelectedItem().toString();

                if(sr_list == "전체") {
                    System.out.println("d");
                    Dept1.setVisible(false);
                    Sex1.setVisible(false);
                    SR_FIELD.setVisible(false);
                    Month.setVisible(false);
                    Emp.setVisible(false);
                }else if(sr_list == "부서별"){
                    Dept1.setVisible(true);
                    Sex1.setVisible(false);
                    SR_FIELD.setVisible(false);
                    Month.setVisible(false);
                    Emp.setVisible(false);
                }else if(sr_list == "성별"){
                    Dept1.setVisible(false);
                    Sex1.setVisible(true);
                    SR_FIELD.setVisible(false);
                    Month.setVisible(false);
                    Emp.setVisible(false);
                } else if(sr_list == "연봉"){
                    Dept1.setVisible(false);
                    Sex1.setVisible(false);
                    SR_FIELD.setVisible(true);
                    Month.setVisible(false);
                    Emp.setVisible(false);
                }else if(sr_list == "생일"){
                    Dept1.setVisible(false);
                    Sex1.setVisible(false);
                    SR_FIELD.setVisible(false);
                    Month.setVisible(true);
                    Emp.setVisible(false);
                }else{
                    Dept1.setVisible(false);
                    Sex1.setVisible(false);
                    SR_FIELD.setVisible(true);
                    Month.setVisible(false);
                    Emp.setVisible(true);
                }
            }
        });

        UD_Category.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ud_list = UD_Category.getSelectedItem().toString();
                //업데이트 리스트
                if(ud_list == "Sex"){
                    Sex2.setVisible(true);
                    Dept2.setVisible(false);
                    UPDATE_FIELD.setVisible(false);
                }else if(ud_list == "부서별 월급"){
                    Sex2.setVisible(false);
                    Dept2.setVisible(true);
                    UPDATE_FIELD.setVisible(true);
                }else{
                    Sex2.setVisible(false);
                    Dept2.setVisible(false);
                    UPDATE_FIELD.setVisible(true);
                }
            }
        });

        SR_Button.addActionListener(this);
        ADD_Button.addActionListener(this);
        DEL_Button.addActionListener(this);
        UD_Button.addActionListener(this);


        setTitle("직원검색시스템");
        setSize(1500,700);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    //직원정보추가창
    class ADD_FORM extends JDialog{
        public ADD_FORM(){

            ADD_Fn.setText("");
            ADD_Mn.setText("");
            ADD_Ln.setText("");
            ADD_Ss.setText("");
            ADD_Bd.setText("");
            ADD_Ad.setText("");
            ADD_Sa.setText("");
            ADD_Su.setText("");
            ADD_Dn.setText("");

            JPanel Title = new JPanel();
            Title.setLayout(new FlowLayout(FlowLayout.CENTER));
            ADDlabel.setFont(new Font("Dialog", Font.BOLD, 16));
            Title.add(ADDlabel);

            JPanel AF = new JPanel();
            AF.setLayout(new FlowLayout((FlowLayout.LEFT)));
            AF.add(new JLabel("First Name :   "));
            AF.add(ADD_Fn);

            JPanel AM = new JPanel();
            AM.setLayout(new FlowLayout((FlowLayout.LEFT)));
            AM.add(new JLabel("Minit Init :   "));
            AM.add(ADD_Mn);

            JPanel AL = new JPanel();
            AL.setLayout(new FlowLayout((FlowLayout.LEFT)));
            AL.add(new JLabel("Last Name :    "));
            AL.add(ADD_Ln);

            JPanel ASs = new JPanel();
            ASs.setLayout(new FlowLayout((FlowLayout.LEFT)));
            ASs.add(new JLabel("Ssn :          "));
            ASs.add(ADD_Ss);


            JPanel AB = new JPanel();
            AB.setLayout(new FlowLayout((FlowLayout.LEFT)));
            AB.add(new JLabel("Birthdate :    "));
            AB.add(ADD_Bd);

            JPanel AA = new JPanel();
            AA.setLayout(new FlowLayout((FlowLayout.LEFT)));
            AA.add(new JLabel("Address :      "));
            AA.add(ADD_Ad);

            JPanel AS = new JPanel();
            AS.setLayout(new FlowLayout((FlowLayout.LEFT)));
            AS.add(new JLabel("Sex :          "));
            String[] sex = {"F", "M"};
            AddSex = new JComboBox(sex);
            AS.add(AddSex);

            JPanel ASa = new JPanel();
            ASa.setLayout(new FlowLayout((FlowLayout.LEFT)));
            ASa.add(new JLabel("Salary :      "));
            ASa.add(ADD_Sa);

            JPanel ASn = new JPanel();
            ASn.setLayout(new FlowLayout((FlowLayout.LEFT)));
            ASn.add(new JLabel("Super_ssn :   "));
            ASn.add(ADD_Su);

            JPanel AD = new JPanel();
            AD.setLayout(new FlowLayout((FlowLayout.LEFT)));
            AD.add(new JLabel("Dno :          "));
            AD.add(ADD_Dn);

            JPanel BT = new JPanel();
            BT.setLayout(new FlowLayout(FlowLayout.CENTER));
            BT.add(ADDF_Button);
            BT.add(ADDC_Button);

            JPanel Final = new JPanel();
            Final.setLayout(new BoxLayout(Final, BoxLayout.Y_AXIS));
            Final.add(Title);
            Final.add(AF);
            Final.add(AM);
            Final.add(AL);
            Final.add(ASs);
            Final.add(AB);
            Final.add(AA);
            Final.add(AS);
            Final.add(ASa);
            Final.add(ASn);
            Final.add(AD);
            Final.add(BT);

            add(Final, BorderLayout.CENTER);
            ADDF_Button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String Fname = ADD_Fn.getText();
                    String Lname = ADD_Ln.getText();
                    String Ssn = ADD_Ss.getText();
                    JFrame frame = new JFrame();
                    if(!Fname.equals("") && !Lname.equals("") && !Ssn.equals("") ){
                        dispose();
                    }else{
                        JOptionPane.showMessageDialog(frame, "이름과 Ssn값을 입력하세요", " ", JOptionPane.WARNING_MESSAGE);
                    }

                }
            });

            ADDC_Button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ADD_Fn.setText("");
                    ADD_Mn.setText("");
                    ADD_Ln.setText("");
                    ADD_Ss.setText("");
                    ADD_Bd.setText("");
                    ADD_Ad.setText("");
                    ADD_Sa.setText("");
                    ADD_Su.setText("");
                    ADD_Dn.setText("");
                    dispose();
                }
            });


            this.setTitle("직원정보추가");
            this.setSize(400,500);
            this.setModal(true);
            this.setLocationRelativeTo(null);
            this.setVisible(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //mysql 연결
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/mydb?serverTimeZone=UTC";
            String user = "root";
            String password = "11111111";
            conn = DriverManager.getConnection(url, user, password);

        } catch (SQLException er) {
            System.err.println("연결 할 수 없습니다");
            er.printStackTrace();
        } catch (ClassNotFoundException er) {
            er.printStackTrace();
        }

        if (check == 1) {
            db.remove(panel);
            revalidate();
        }

        //검색
        if(e.getSource() == SR_Button){
            if (Name_ck.isSelected() || Ssn_ck.isSelected() || Bdate_ck.isSelected() || Add_ck.isSelected() || Sex_ck.isSelected()
                    || Sal_ck.isSelected() || Sup_ck.isSelected() || Dep_ck.isSelected()) {
                EMPlist.clear();
                EMPlist.add("선택");

                String sel = "select";
                if (Name_ck.isSelected()) {
                    sel += " concat(e1.fname,' ', e1.minit,' ', e1.lname,' ') as Name";
                    EMPlist.add("NAME");
                }
                if (Ssn_ck.isSelected()) {
                    if (!Name_ck.isSelected())
                        sel += " e1.ssn";
                    else
                        sel += ", e1.ssn";
                    EMPlist.add("SSN");
                }
                if (Bdate_ck.isSelected()) {
                    if (!Name_ck.isSelected() && !Ssn_ck.isSelected())
                        sel += " e1.bdate";
                    else
                        sel += ", e1.bdate";
                    EMPlist.add("BDATE");
                }
                if (Add_ck.isSelected()) {
                    if (!Name_ck.isSelected() && !Ssn_ck.isSelected() && !Bdate_ck.isSelected())
                        sel += " e1.address";
                    else
                        sel += ", e1.address";
                    EMPlist.add("ADDRESS");
                }
                if (Sex_ck.isSelected()) {
                    if (!Name_ck.isSelected() && !Ssn_ck.isSelected() && !Bdate_ck.isSelected() && !Add_ck.isSelected())
                        sel += " e1.sex";
                    else
                        sel += ", e1.sex";
                    EMPlist.add("SEX");
                }
                if (Sal_ck.isSelected()) {
                    if (!Name_ck.isSelected() && !Ssn_ck.isSelected() && !Bdate_ck.isSelected() && !Add_ck.isSelected()
                            && !Sex_ck.isSelected())
                        sel += " e1.salary";
                    else
                        sel += ", e1.salary";
                    EMPlist.add("SALARY");
                }
                if (Sup_ck.isSelected()) {
                    if (!Name_ck.isSelected() && !Ssn_ck.isSelected() && !Bdate_ck.isSelected() && !Add_ck.isSelected() && !Sex_ck.isSelected()
                            && !Sal_ck.isSelected())
                        sel += " concat(e2.fname, ' ', e2.minit, ' ',e2.lname,' ') as Supervisor ";
                    else
                        sel += ", concat(e2.fname, ' ', e2.minit, ' ',e2.lname,' ') as Supervisor ";
                    EMPlist.add("SUPERVISOR");
                }
                if (Dep_ck.isSelected()) {
                    if (!Name_ck.isSelected() && !Ssn_ck.isSelected() && !Bdate_ck.isSelected() && !Add_ck.isSelected() && !Sex_ck.isSelected()
                            && !Sal_ck.isSelected() && !Sup_ck.isSelected())
                        sel += " dname";
                    else
                        sel += ", dname";
                    EMPlist.add("DEPARTMENT");
                }
                String st = "";


                if(SR_Category.getSelectedItem().toString() == "가족"){
                    useField = 1;
                    sel += ", Dependent_name, d.Sex, d.Bdate, d.Relationship";
                    EMPlist.add("Depen_name");
                    EMPlist.add("Depen_Sex");
                    EMPlist.add("Depen_Bdate");
                    EMPlist.add("Relationship");
                    st = sel + " FROM employee e1 left outer join department on e1.dno = dnumber left outer join employee e2 ON e1.super_ssn=e2.ssn, Dependent d where e1.ssn = d.Essn ";
                    useField = 1;
                    if(Emp.getSelectedItem().toString() == "Fname"){
                        st += "and e1.Fname = ? ";
                    }else if(Emp.getSelectedItem().toString() == "Minit"){
                        st += "and e1.Minit = ? ";
                    }else if(Emp.getSelectedItem().toString() == "Lname"){
                        st += "and e1.Lname = ? ";
                    }

                }else{
                    st = sel +  " FROM employee e1 left outer join department on e1.dno = dnumber left outer join employee e2 ON e1.super_ssn=e2.ssn ";
                    if (SR_Category.getSelectedItem().toString() == "부서별") {
                        if (Dept1.getSelectedItem().toString() == "Research")
                            st += " where dname = \"Research\";";
                        else if (Dept1.getSelectedItem().toString() == "Administration")
                            st += " where dname = \"Administration\";";
                        else if (Dept1.getSelectedItem().toString() == "Headquarters")
                            st += " where dname = \"Headquarters\";";
                    }

                    else if(SR_Category.getSelectedItem().toString() == "성별") {
                        if (Sex1.getSelectedItem().toString() == "F")
                            st += " where e1.sex = \"F\";";
                        else if (Sex1.getSelectedItem().toString() == "M")
                            st += " where e1.sex = \"M\";";
                    }

                    else if(SR_Category.getSelectedItem().toString() == "연봉") {
                        useField = 1;
                        st += " where e1.salary >= ?";
                    }

                    else if(SR_Category.getSelectedItem().toString() == "생일") {
                        if(Month.getSelectedItem().toString() == "1"){
                            st += " where e1.BDATE LIKE \"_____01___\";";
                        }else if(Month.getSelectedItem().toString() == "2"){
                            st += " where e1.BDATE LIKE \"_____02___\";";
                        }else if(Month.getSelectedItem().toString() == "3"){
                            st += " where e1.BDATE LIKE \"_____03___\";";
                        }else if(Month.getSelectedItem().toString() == "4"){
                            st += " where e1.BDATE LIKE \"_____04___\";";
                        }else if(Month.getSelectedItem().toString() == "5"){
                            st += " where e1.BDATE LIKE \"_____05___\";";
                        }else if(Month.getSelectedItem().toString() == "6"){
                            st += " where e1.BDATE LIKE \"_____06___\";";
                        }else if(Month.getSelectedItem().toString() == "7"){
                            st += " where e1.BDATE LIKE \"_____07___\";";
                        }else if(Month.getSelectedItem().toString() == "8"){
                            st += " where e1.BDATE LIKE \"_____08___\";";
                        }else if(Month.getSelectedItem().toString() == "9"){
                            st += " where e1.BDATE LIKE \"_____09___\";";
                        }else if(Month.getSelectedItem().toString() == "10"){
                            st += " where e1.BDATE LIKE \"_____10___\";";
                        }else if(Month.getSelectedItem().toString() == "11"){
                            st += " where e1.BDATE LIKE \"_____11___\";";
                        }else if(Month.getSelectedItem().toString() == "12"){
                            st += " where e1.BDATE LIKE \"_____12___\";";
                        }

                    }
                    else if(SR_Category.getSelectedItem().toString() == "부하직원") {
                        useField = 1;
                        if(Emp.getSelectedItem().toString() == "Fname"){
                            st += "where e1.Super_ssn = e2.ssn and  e2.Fname = ?";
                        }else if(Emp.getSelectedItem().toString() == "Minit"){
                            st += "where e1.Super_ssn = e2.ssn and  e2.Minit = ?";
                        }else if(Emp.getSelectedItem().toString() == "Lname"){
                            st += "where e1.Super_ssn = e2.ssn and  e2.Lname = ?";
                        }
                    }
                }



                model = new DefaultTableModel(EMPlist, 0) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        if (column > 0) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                };

                for (int i = 0; i < EMPlist.size(); i++) {
                    if (EMPlist.get(i) == "NAME") {
                        NameCount = i;
                    }

                }
                    table = new JTable(model) {
                    @Override
                    public Class getColumnClass(int column) {
                        if (column == 0) {
                            return Boolean.class;
                        } else
                            return String.class;
                    }
                };

                SEL_EMP.setText(" ");

                try {
                    check = 1;
                    if(useField == 0){
                        stmt = conn.createStatement();
                        rs = stmt.executeQuery(st);
                    } else if(useField == 1){
                        PreparedStatement ps = conn.prepareStatement(st);
                        ps.clearParameters();
                        String search = SR_FIELD.getText();
                        ps.setString(1, search);
                        rs = ps.executeQuery();

                    }

                    ResultSetMetaData rsmd = rs.getMetaData();
                    int columnCnt= rsmd.getColumnCount();
                    int rowCnt = table.getRowCount();

                    while (rs.next()) {
                        Vector<Object> tuple = new Vector<Object>();
                        tuple.add(false);
                        for (int i = 1; i < columnCnt + 1; i++) {
                            tuple.add(rs.getString(rsmd.getColumnName(i)));
                        }
                        model.addRow(tuple);
                        rowCnt++;
                    }
                    totalNum.setText(String.valueOf(rowCnt));

                } catch (SQLException ee) {
                    System.out.println("actionPerformed err : " + ee);
                    ee.printStackTrace();
                }

                panel = new JPanel();
                scrollPane = new JScrollPane(table);
                table.getModel().addTableModelListener(new COMPANY_JDBC.CheckBoxModelListener());
                scrollPane.setPreferredSize(new Dimension(1200, 350));
                panel.add(scrollPane);
                add(panel, BorderLayout.CENTER);
                SR_FIELD.setText("");
                revalidate();
                useField = 0;

            } else {
                JOptionPane.showMessageDialog(null, "검색 항목을 선택하세요.");
            }
        }

        //수정
        if(e.getSource() == UD_Button){
            Vector<String> update_list = new Vector<String>();

            if(UD_Category.getSelectedItem().toString() == "Address"){
                try {
                    String columnName = model.getColumnName(8);
                    if (columnName == "DEPARTMENT") {
                        for (int i = 0; i < table.getRowCount(); i++) {
                            if (table.getValueAt(i, 0) == Boolean.TRUE) {
                                update_list.add((String) table.getValueAt(i, 2));
                                String updateAddress = UPDATE_FIELD.getText();
                                table.setValueAt(updateAddress, i, 4);
                            }
                        }
                        for (int i = 0; i < update_list.size(); i++) {
                            String updateStmt = "UPDATE EMPLOYEE SET Address=?, Modified=? WHERE Ssn=?";
                            PreparedStatement ps = conn.prepareStatement(updateStmt);
                            ps.clearParameters();
                            String updateAddress = UPDATE_FIELD.getText();
                            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            ps.setString(1, updateAddress);
                            ps.setString(2, sdf.format(timestamp));
                            ps.setString(3, String.valueOf(update_list.get(i)));
                            ps.executeUpdate();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "수정, 삭제 작업은 검색 항목을 모두 체크하고 실행해주세요.");
                    }

                    SEL_EMP.setText(" ");

                } catch (SQLException e1) {
                    System.out.println("actionPerformed err : " + e1);
                    e1.printStackTrace();
                }


            }else if(UD_Category.getSelectedItem().toString() == "Sex"){
                try {
                    String columnName = model.getColumnName(8);
                    if (columnName == "DEPARTMENT") {
                        for (int i = 0; i < table.getRowCount(); i++) {
                            if (table.getValueAt(i, 0) == Boolean.TRUE) {
                                update_list.add((String) table.getValueAt(i, 2));
                                String updateSex = Sex2.getSelectedItem().toString();
                                table.setValueAt(updateSex, i, 5);
                            }
                        }
                        for (int i = 0; i < update_list.size(); i++) {
                            String updateStmt = "UPDATE EMPLOYEE SET Sex=?, Modified=? WHERE Ssn=?";
                            PreparedStatement ps = conn.prepareStatement(updateStmt);
                            ps.clearParameters();
                            String updateSex = Sex2.getSelectedItem().toString();
                            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            ps.setString(1, updateSex);
                            ps.setString(2, sdf.format(timestamp));
                            ps.setString(3, String.valueOf(update_list.get(i)));
                            ps.executeUpdate();

                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "수정, 삭제 작업은 검색 항목을 모두 체크하고 실행해주세요.");
                    }

                    SEL_EMP.setText(" ");

                } catch (SQLException e1) {
                    System.out.println("actionPerformed err : " + e1);
                    e1.printStackTrace();
                }

            }else if(UD_Category.getSelectedItem().toString() == "Salary"){
                try {
                    String columnName = model.getColumnName(8);
                    if (columnName == "DEPARTMENT") {
                        for (int i = 0; i < table.getRowCount(); i++) {
                            if (table.getValueAt(i, 0) == Boolean.TRUE) {
                                update_list.add((String) table.getValueAt(i, 2));
                                String updateSalary = UPDATE_FIELD.getText();
                                table.setValueAt(Double.parseDouble(updateSalary), i, 2);
                            }
                        }
                        for (int i = 0; i < update_list.size(); i++) {
                            String updateStmt = "UPDATE EMPLOYEE SET Salary=?, Modified=? WHERE Ssn=?";
                            PreparedStatement ps = conn.prepareStatement(updateStmt);
                            ps.clearParameters();
                            String updateSalary = UPDATE_FIELD.getText();
                            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            ps.setString(1, updateSalary);
                            ps.setString(2, sdf.format(timestamp));
                            ps.setString(3, String.valueOf(update_list.get(i)));
                            ps.executeUpdate();

                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "수정, 삭제 작업은 검색 항목을 모두 체크하고 실행해주세요.");
                    }

                    SEL_EMP.setText(" ");

                } catch (SQLException e1) {
                    System.out.println("actionPerformed err : " + e1);
                    e1.printStackTrace();
                }

            }else if(UD_Category.getSelectedItem().toString() == "부서별 월급"){
                try {
                    String columnName = model.getColumnName(8);
                    if (columnName == "DEPARTMENT") {
                        String updateDep = Dept2.getSelectedItem().toString();
                        if(updateDep == "Research") {
                            for (int i = 0; i < table.getRowCount(); i++) {
                                String DeptCheck = (String) table.getValueAt(i, 8);
                                if (DeptCheck.equals("Research")) {
                                    System.out.println("HW1.actionPerformed");
                                    update_list.add((String) table.getValueAt(i, 2));
                                    String updateSalary = UPDATE_FIELD.getText();
                                    table.setValueAt(Double.parseDouble(updateSalary), i, 6);
                                }
                            }
                        }else if (updateDep == "Administration"){
                            for (int i = 0; i < table.getRowCount(); i++) {
                                String DeptCheck = (String) table.getValueAt(i, 8);
                                if (DeptCheck.equals("Administration")) {
                                    update_list.add((String) table.getValueAt(i, 2));
                                    String updateSalary = UPDATE_FIELD.getText();
                                    table.setValueAt(Double.parseDouble(updateSalary), i, 6);
                                }
                            }
                        }else if( updateDep == "Headquarters"){
                            for (int i = 0; i < table.getRowCount(); i++) {
                                String DeptCheck = (String) table.getValueAt(i, 8);
                                if (DeptCheck.equals("Headquarters")) {
                                    update_list.add((String) table.getValueAt(i, 2));
                                    String updateSalary = UPDATE_FIELD.getText();
                                    table.setValueAt(Double.parseDouble(updateSalary), i, 6);
                                }
                            }

                        }
                        for (int i = 0; i < update_list.size(); i++) {
                            String updateStmt = "UPDATE EMPLOYEE SET Salary=?, Modified=? WHERE Ssn=?";
                            PreparedStatement ps = conn.prepareStatement(updateStmt);
                            ps.clearParameters();
                            String updateSalary = UPDATE_FIELD.getText();
                            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            ps.setString(1, updateSalary);
                            ps.setString(2, sdf.format(timestamp));
                            ps.setString(3, String.valueOf(update_list.get(i)));
                            ps.executeUpdate();

                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "수정, 삭제 작업은 검색 항목을 모두 체크하고 실행해주세요.");
                    }

                    SEL_EMP.setText(" ");

                } catch (SQLException e1) {
                    System.out.println("actionPerformed err : " + e1);
                    e1.printStackTrace();
                }
            }


            panel = new JPanel();
            scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(1200, 350));
            panel.add(scrollPane);
            add(panel, BorderLayout.CENTER);
            revalidate();
        }

       //삭제
        if(e.getSource() == DEL_Button){
            Vector<String> delete_list = new Vector<String>();

            try {
                String columnName = model.getColumnName(8);
                if (columnName == "DEPARTMENT") {
                    for (int i = 0; i < table.getRowCount(); i++) {
                        if (table.getValueAt(i, 0) == Boolean.TRUE) {
                            delete_list.add((String) table.getValueAt(i, 2));
                        }
                    }
                    for (int i = 0; i < delete_list.size(); i++) {
                        for (int k = 0; k < model.getRowCount(); k++) {
                            if (table.getValueAt(k, 0) == Boolean.TRUE) {
                                model.removeRow(k);
                                totalNum.setText(String.valueOf(table.getRowCount()));
                            }
                        }
                    }
                    for (int i = 0; i < delete_list.size(); i++) {
                        String deleteStmt = "DELETE FROM EMPLOYEE WHERE Ssn=?";
                        PreparedStatement ps = conn.prepareStatement(deleteStmt);
                        ps.clearParameters();
                        ps.setString(1, String.valueOf(delete_list.get(i)));
                        ps.executeUpdate();

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "수정, 삭제 작업은 검색 항목을 모두 체크하고 실행해주세요.");
                }
                SEL_EMP.setText(" ");
            } catch (SQLException e1) {
                System.out.println("actionPerformed err : " + e1);
                e1.printStackTrace();
            }
            panel = new JPanel();
            scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(1200, 350));
            panel.add(scrollPane);
            add(panel, BorderLayout.CENTER);
            revalidate();
            UPDATE_FIELD.setText("");
        }


        //추가
        if(e.getSource() == ADD_Button){
            Vector<String> ADD_list = new Vector<String>();
            int ADDCK = 0;

            new ADD_FORM();
            String Fname = ADD_Fn.getText();
            String Lname = ADD_Ln.getText();
            String Ssn = ADD_Ss.getText();
            if(!Ssn.equals("") && !Fname.equals("") && !Lname.equals("")) {
                try {
                    ADDCK = 2;
                    String updateStmt = "INSERT INTO EMPLOYEE VALUES (?, ?, ? ,? ,? ,? ,?, ?, ?, ?, ?, ?)";
                    PreparedStatement ps = conn.prepareStatement(updateStmt);
                    ps.clearParameters();
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    ps.setString(1, ADD_Fn.getText());

                    if(ADD_Mn.getText().equals(""))
                        ps.setString(2,null);
                    else
                        ps.setString(2, ADD_Mn.getText());

                    ps.setString(3, ADD_Ln.getText());
                    ps.setString(4, ADD_Ss.getText());

                    if(ADD_Bd.getText().equals(""))
                        ps.setString(5,null);
                    else
                        ps.setString(5, ADD_Bd.getText());

                    if(ADD_Ad.getText().equals(""))
                        ps.setString(6,null);
                    else
                        ps.setString(6, ADD_Ad.getText());

                    ps.setString(7, AddSex.getSelectedItem().toString());

                    if(ADD_Sa.getText().equals(""))
                        ps.setString(8,null);
                    else
                        ps.setString(8, ADD_Sa.getText());

                    if(ADD_Su.getText().equals(""))
                        ps.setString(9,null);
                    else
                        ps.setString(9, ADD_Su.getText());

                    if(ADD_Dn.getText().equals(""))
                        ps.setString(10, Integer.toString(1));
                    else
                        ps.setString(10, ADD_Dn.getText());

                    ps.setString(11, sdf.format(timestamp));
                    ps.setString(12, sdf.format(timestamp));
                    ps.executeUpdate();


                } catch (SQLException e1) {
                    ADDCK = 1;
                    JOptionPane.showMessageDialog(null, "정보 추가 실패");
                    System.out.println("actionPerformed err : " + e1);
                    e1.printStackTrace();
                }
            }
            if(ADDCK == 2) JOptionPane.showMessageDialog(null, "정보 추가 완료");

            ADD_Fn.setText("");
            ADD_Mn.setText("");
            ADD_Ln.setText("");
            ADD_Ss.setText("");
            ADD_Bd.setText("");
            ADD_Ad.setText("");
            ADD_Sa.setText("");
            ADD_Su.setText("");
            ADD_Dn.setText("");
        }


    }
    public class CheckBoxModelListener implements TableModelListener {
        public void tableChanged(TableModelEvent e) {
            int row = e.getFirstRow();
            int column = e.getColumn();
            if (column == Check_Column) {
                TableModel model = (TableModel) e.getSource();
                String columnName = model.getColumnName(1);
                Boolean checked = (Boolean) model.getValueAt(row, column);
                if (columnName == "NAME") {
                    if (checked) {
                        namelist = "";
                        for (int i = 0; i < table.getRowCount(); i++) {
                            if (table.getValueAt(i, 0) == Boolean.TRUE) {
                                namelist += (String) table.getValueAt(i, NameCount) + "    ";

                            }
                        }
                        SEL_EMP.setText(namelist);
                    } else {
                        namelist = "";
                        for (int i = 0; i < table.getRowCount(); i++) {
                            if (table.getValueAt(i, 0) == Boolean.TRUE) {
                                namelist += (String) table.getValueAt(i, 1) + "    ";

                            }
                        }
                        SEL_EMP.setText(namelist);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        new COMPANY_JDBC();
    }

}
