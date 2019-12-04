/**
 *
 * @author London Brunell
 */
public class ImmovableSpaceException extends Exception {

    /**
     * Constructor for ImmovableSpaceException without a message.
     * 
     */
    public ImmovableSpaceException() {
    }

    /**
     * Constructor for ImmovableSpaceException with a message.
     *
     * @param msg the detail message.
     */
    public ImmovableSpaceException(String msg) {
        super(msg);
    }
}
