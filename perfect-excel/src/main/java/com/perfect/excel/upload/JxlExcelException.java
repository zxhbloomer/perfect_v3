package com.perfect.excel.upload;

/**
 * @author zxh
 */
public class JxlExcelException extends RuntimeException {

	private static final long serialVersionUID = 1830974553436749465L;

	public JxlExcelException() {

	}

	public JxlExcelException(String message) {
		super(message);
	}

	public JxlExcelException(Throwable cause) {
		super(cause);
	}

	public JxlExcelException(String message, Throwable cause) {
		super(message, cause);
	}

	public JxlExcelException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
