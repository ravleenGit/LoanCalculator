import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class LoanProjection {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JComboBox comboBox1;
    private JTable table1;
    private JTable table2;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JTextField monthlyPaymentTF;
    private JPanel rootPanel;

    public void setTextField1(String textField1) {
        this.textField1.setText(textField1);
    }
    public void setTextField2(String textField2) {
        this.textField2.setText(textField2);
    }
    public void setTextField3(String textField3) {
        this.textField3.setText(textField3);
    }
    public void setTextField4(String textField4) {
        this.textField4.setText(textField4);
    }

    public void setComboBox1(JComboBox comboBox1) {
        this.comboBox1 = comboBox1;
    }

    private ArrayList<LoanP> loanPArrayList;
    int selectedItemIndex;

    public JPanel getRootPanel() {
        return rootPanel;
    }

    //Constructor of Form Class
    public LoanProjection() {

        //Get all loans at starting
        getAllLoans();

        //Add Button listener
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addLoan();
            }
        });

        //Item in table1 click listener
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedItemIndex= table1.getSelectedRow();
                LoanP loanSelected = loanPArrayList.get(selectedItemIndex);

                textField1.setText(loanSelected.getClientno());
                textField2.setText(loanSelected.getClientname());
                textField3.setText(String.valueOf(loanSelected.getLoanamount()));
                textField4.setText(String.valueOf(loanSelected.getYears()));
                comboBox1.setSelectedItem(loanSelected.getLoantype());

                if(loanSelected.getLoantype().equals("Business")){
                    Business busObj = new Business(loanSelected.getClientno(),loanSelected.getClientname(),
                            loanSelected.getLoanamount(), loanSelected.getYears(), loanSelected.getLoantype());
                    table2.setModel(busObj.generateTable());
                    monthlyPaymentTF.setText(String.valueOf(busObj.calculateMonthlyPayment()));
                }
                else if(loanSelected.getLoantype().equals("Personal")){
                    Personal perObj = new Personal(loanSelected.getClientno(),loanSelected.getClientname(),
                            loanSelected.getLoanamount(), loanSelected.getYears(), loanSelected.getLoantype());
                    table2.setModel(perObj.generateTable());
                    monthlyPaymentTF.setText(String.valueOf(perObj.calculateMonthlyPayment()));
                }

            }
        });

        //Edit button listener
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editLoan();
            }
        });

        //Delete button listener
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteLoan();
            }
        });
    }


    //Method to empty all the input fields
    public void emptyAllInputs(){
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        monthlyPaymentTF.setText("");
    }

    //Method to check the input field values
    public boolean validateInput(){
        boolean returnValue = true;
        if(textField1.getText() == null || textField1.getText().isEmpty()){
            JOptionPane.showMessageDialog(rootPanel,"Please enter Client Number");
        }
        else if(textField2.getText() == null || textField2.getText().isEmpty()){
            JOptionPane.showMessageDialog(rootPanel,"Please enter Client Number");
        }
        else if(textField3.getText() == null || textField3.getText().isEmpty()){
            JOptionPane.showMessageDialog(rootPanel,"Please enter Client Number");
        }
        else if(textField4.getText() == null || textField4.getText().isEmpty()){
            JOptionPane.showMessageDialog(rootPanel,"Please enter Client Number");
        }
        return returnValue;
    }

    //Method to get all loan records from database
    public void getAllLoans(){
        try
        {
            loanPArrayList = new ArrayList<LoanP>();
            int c;
            Class.forName("com.mysql.jdbc.Driver");

            //Connection object to our database
            Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/loan", "root", "");

            //sql query to get all records
            PreparedStatement querySelect = con1.prepareStatement("Select * from loantable");

            //result of get query
            ResultSet rs = querySelect.executeQuery();


            ResultSetMetaData Res = rs.getMetaData();
            c = Res.getColumnCount();

            if(c > 0){
                //Creating DefaultTableModel
                DefaultTableModel df = new DefaultTableModel(
                        null,
                        new String[]{"Number", "Name", "Amount", "Years", "Type of Loan"}
                );


                while (rs.next()) {

                    LoanP loanObj = new LoanP(rs.getString("clientno"),
                            rs.getString("clientname"),
                            rs.getDouble("loanamount"),
                            rs.getInt("years"),
                            rs.getString("loantype"));

                    loanPArrayList.add(loanObj);

                    //Creating vector which acts as a row
                    Vector v2 = new Vector();

                    v2.add(loanObj.getClientno());
                    v2.add(loanObj.getClientname());
                    v2.add(loanObj.getLoanamount());
                    v2.add(loanObj.getYears());
                    v2.add(loanObj.getLoantype());


                    //Adding row to DataModel
                    df.addRow(v2);

                }

                //Setting DataModel to UI
                table1.setModel(df);
            }


        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }


    //Method to Add loan
    public void addLoan(){
        PreparedStatement query;

            try
            {
                if(validateInput()) {
                    String clientno = textField1.getText();
                    String clientname = textField2.getText();
                    double loanamount = Double.parseDouble(textField3.getText());
                    int years = Integer.parseInt(textField4.getText());
                    String loantype = String.valueOf(comboBox1.getSelectedItem());
                    LoanP loanObj = new LoanP(clientno, clientname, loanamount, years, loantype);

                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/loan", "root", "");
                    System.out.println(con1);
                    query = con1.prepareStatement("insert into loantable values(?,?,?,?,?)");
                    query.setString(1, clientno);
                    query.setString(2, clientname);
                    query.setDouble(3, loanamount);
                    query.setInt(4, years);
                    query.setString(5, loantype);

                    System.out.println(query);

                    int result = query.executeUpdate();
                    System.out.println(result);
                    if (result > 0) {
                        emptyAllInputs();
                        getAllLoans();
                        JOptionPane.showMessageDialog(rootPanel, "Record Added");
                    }
                }
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
    }

    public void editLoan(){

        try
        {
            if(validateInput()){
                LoanP loanPToEdit = loanPArrayList.get(selectedItemIndex);
                PreparedStatement query;

                String clientno = textField1.getText();
                String clientname = textField2.getText();
                double loanamount = Double.parseDouble(textField3.getText());
                int years = Integer.parseInt(textField4.getText());
                String loantype = String.valueOf(comboBox1.getSelectedItem());

                LoanP loanPAfterEdit = new LoanP(clientno, clientname, loanamount, years, loantype);

                Class.forName("com.mysql.jdbc.Driver");
                Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/loan","root","");
                System.out.println(con1);
                query = con1.prepareStatement("update loantable set clientno = ?, clientname = ?, " +
                        "loanamount = ?, years = ?, loantype = ? where clientno = ?");
                query.setString(1, loanPAfterEdit.getClientno());
                query.setString(2, loanPAfterEdit.getClientname());
                query.setDouble(3, loanPAfterEdit.getLoanamount());
                query.setInt(4, loanPAfterEdit.getYears());
                query.setString(5, loanPAfterEdit.getLoantype());
                query.setString(6, loanPToEdit.getClientno());

                System.out.println(query);

                int result = query.executeUpdate();
                System.out.println(result);
                if (result> 0)
                {
                    emptyAllInputs();
                    getAllLoans();
                    JOptionPane.showMessageDialog(rootPanel,"Record Edited");
                }
            }

        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }


    public void deleteLoan(){
        LoanP loanToDelete = loanPArrayList.get(selectedItemIndex);
        PreparedStatement query;

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/loan","root","");
            System.out.println(con1);
            query = con1.prepareStatement("delete from loantable where clientno = ?");
            query.setString(1, loanToDelete.getClientno());

            System.out.println(query);

            int result = query.executeUpdate();
            System.out.println(result);
            if (result> 0)
            {
                emptyAllInputs();
                getAllLoans();
                JOptionPane.showMessageDialog(rootPanel,"Record deleted");
            }
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

}
