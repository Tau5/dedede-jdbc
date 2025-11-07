package dedede.repository;

import java.io.Serial;

public class InvalidRowException extends Exception {
    @Serial
    private static final long serialVersionUID = -8631699000166883645L;

    public InvalidRowException(String msg) {
        super(msg);
    }
}
