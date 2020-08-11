import javax.swing.table.DefaultTableModel;
import java.text.DecimalFormat;
import java.util.Vector;

public class Personal extends LoanP implements Generate{

    private final double interestRate = 0.06;

    public Personal(String clientno, String clientname, double loanamount, int years, String loantype) {
        super(clientno, clientname, loanamount, years, loantype);
    }

    public double calculateMonthlyPayment(){
        int months = this.getYears() * 12;
        double loanAmount = this.getLoanamount();
        double monthlyRate = interestRate/months;
        double monthlyPayment = (loanAmount*monthlyRate) / (1-Math.pow(1+monthlyRate, -months));
        DecimalFormat format = new DecimalFormat("##.00");
        monthlyPayment = Double.valueOf(format.format(monthlyPayment));
        return monthlyPayment;
    }

    @Override
    public DefaultTableModel generateTable() {
        double monthlyPayment = this.calculateMonthlyPayment();
        System.out.println(monthlyPayment);

        int numOfMonths = this.getYears() * 12;
        double loanAmount = this.getLoanamount();

        DefaultTableModel dm = new DefaultTableModel(
                null,
                new String[]{"Payment", "Principal", "Interest", "Monthly Payment", "Balance"}
        );

        Vector v0 = new Vector();
        v0.add(0);
        v0.add(0.0);
        v0.add(0.0);
        v0.add(0.0);
        v0.add(loanAmount);
        dm.addRow(v0);

        DecimalFormat format = new DecimalFormat("##.00");
        for(int i = 1; i <= numOfMonths; i++){

            Vector v = new Vector();
            double interestAmount = Double.valueOf(format.format((loanAmount*interestRate)/numOfMonths));
            double principal = Double.valueOf(format.format( monthlyPayment - interestAmount));
            v.add(i);
            v.add(principal);
            v.add(interestAmount);
            v.add(monthlyPayment);

            loanAmount = Double.valueOf(format.format(loanAmount - monthlyPayment));

            v.add(loanAmount);

            dm.addRow(v);



        }
        return dm;
    }
}
