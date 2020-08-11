//LoanP class which acts as Data Structure for Loan object
public class LoanP
{

    //declaring variables
    private String clientno;
    private String clientname;
    private double loanamount;
    private int years;
    private String loantype;

    //constructor to fill up data inside the fields
    public LoanP(String clientno, String clientname, double loanamount, int years, String loantype)
    {
        this.clientno = clientno;
        this.clientname = clientname;
        this.loanamount = loanamount;
        this.years = years;
        this.loantype = loantype;
    }

    //getter setter methods for fields
    public void setClientno(String clientno)
    {
        this.clientno = clientno;
    }

    public void setClientname(String clientname)
    {
        this.clientname = clientname;
    }

    public void setLoanamount(double loanamount)
    {
        this.loanamount = loanamount;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public void setLoantype(String loantype) {
        this.loantype = loantype;
    }


    public String getClientno() {
        return clientno;
    }

    public String getClientname() {
        return clientname;
    }

    public double getLoanamount() {
        return loanamount;
    }

    public int getYears() {
        return years;
    }

    public String getLoantype() {
        return loantype;
    }
}
