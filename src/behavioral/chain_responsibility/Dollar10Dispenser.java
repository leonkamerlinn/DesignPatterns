package behavioral.chain_responsibility;

public class Dollar10Dispenser implements DispenseChain {
    private DispenseChain chain;

    @Override
    public void setNextChain(DispenseChain nextChain) {

        this.chain = nextChain;
    }

    @Override
    public void dispense(Currency cur) {

        if (cur.getAmount() >= 10) {
            int num = cur.getAmount() / 10;
            int remainder = cur.getAmount() % 10;
            System.out.println("Dispensing " + num + " 10$ note");
            if (remainder != 0 && chain != null) this.chain.dispense(new Currency(remainder));
        } else {
            if (chain != null) {
                this.chain.dispense(cur);
            }
        }
    }
}
