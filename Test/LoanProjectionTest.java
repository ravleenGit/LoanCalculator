import static org.junit.jupiter.api.Assertions.*;

class LoanProjectionTest {

    @org.junit.jupiter.api.Test
    void addLoan() {
        LoanProjection loanProjection = new LoanProjection();
        loanProjection.setTextField1("9999");
        loanProjection.setTextField2("Ravleen test");
        loanProjection.setTextField3("1000");
        loanProjection.setTextField4("1");

        loanProjection.addLoan();

    }

    @org.junit.jupiter.api.Test
    void deleteLoan() {
    }
}