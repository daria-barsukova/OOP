package dbarsukova.order;


/**
 * Order class represents order with unique number and preparation time.
 * it also tracks state of order as it progresses through delivery process.
 */

public class Order {
    private final int id;
    private final int time;
    private State state;

    /**
     * constructs new Order object with specified order number and preparation time.
     *
     * @param id   unique number of order.
     * @param time preparation time of order.
     */
    public Order(int id, int time) {
        this.id = id;
        this.state = State.PENDING;
        this.time = time;
    }

    /**
     * returns preparation time of order.
     *
     * @return preparation time of order.
     */
    public int getTime() {
        return this.time;
    }

    /**
     * prints current state of order.
     */
    public void printState() {
        System.out.printf("%7d -- %11s\n", this.id, this.state);
    }

    /**
     * updates state of order to next state in delivery process.
     */
    public void updateState() {
        State[] values = State.values();
        int next = this.state.ordinal() + 1;
        if (next < values.length) {
            this.state = values[next];
        }
    }

    /**
     * State enum represents possible states of order.
     */
    private enum State {
        PENDING("pending"),
        COOKING("cooking"),
        COOKED("cooked"),
        DELIVERING("delivering"),
        COMPLETED("completed");
        private final String label;

        State(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return this.label;
        }
    }
}
