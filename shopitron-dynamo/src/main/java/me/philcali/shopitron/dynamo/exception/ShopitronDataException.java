package me.philcali.shopitron.dynamo.exception;

public class ShopitronDataException extends RuntimeException {
    private static final long serialVersionUID = -4351828634240496012L;

    public ShopitronDataException(Throwable t) {
        super(t);
    }
}
