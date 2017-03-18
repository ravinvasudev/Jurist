package in.co.jurist.util;

public class Constants {

	public static final String BLANK_STRING = "";

	public static final String SPACE_DELIMITER = " ";

	public static final String COMMA_DELIMITER = ",";

	public static final String HYPHEN_DELIMITER = "-";

	public static final String SQL_STRING_OR = " or ";

	public static final String EXACT_MATCHED = "EXACT_MATCHED";

	public static final String LESS_MATCHED = "LESS_MATCHED";

	public static final int PERFECT_MATCH = 1;

	public static final int HALF_LENGTH = 2;

	public static final String REFERER_PAGE = "Referer";

	public enum InitParam {

		RESTRICTED_PAGES("RestrictedPages"),

		LOGOUT_PAGES("LogoutPages"),
		
		SESSION_TIMEOUT("SessionTimeout");

		private String val;

		private InitParam(String val) {
			this.val = val;
		}

		public String get() {
			return val;
		}
	}

	public enum Delimiter {

		COMMA(","),

		SLASH("/");

		private String delimiter;

		private Delimiter(String delimiter) {
			this.delimiter = delimiter;
		}

		public String get() {
			return delimiter;
		}

	}

	public enum InitValue {

		ZERO(0),

		SESSION_TIMEOUT(1800);

		private int val;

		private InitValue(int val) {
			this.val = val;
		}

		public int get() {
			return val;
		}
	}

	public enum RequestParam {

		USERNAME("username");

		private String val;

		private RequestParam(String val) {
			this.val = val;
		}

		public String get() {
			return val;
		}
	}

	public enum Attribute {

		USERNAME("username");

		private String val;

		private Attribute(String val) {
			this.val = val;
		}

		public String get() {
			return val;
		}
	}

	public enum NamedQuery {
		GET_CATEGORIES("Category.findAll");

		private final String val;

		private NamedQuery(String val) {
			this.val = val;
		}

		public String getValue() {
			return this.val;
		}
	}

	public enum Status {
		NO_SUCH_RECORD(-4),

		INPUT_MISSING(-3),

		RECORD_EXISTS(-2),

		INITIAL(-1),

		FAILURE(0),

		SUCCESS(1);

		private int val;

		private Status(int val) {
			this.val = val;
		}

		public int getStatus() {
			return this.val;
		}
	}

	public static enum ResultType {

		ACT, PARAGRAPH, AMENDMENT
	}

	public enum AccountType {
		JURIST("0"),

		GOOGLE("1"),

		FACEBOOK("2");

		private String val;

		private AccountType(String val) {
			this.val = val;
		}

		public String get() {
			return this.val;
		}
	}

	public static enum VoteType {

		QUESTION("1"), ANSWER("2");

		private String val;

		private VoteType(String val) {
			this.val = val;
		}

		public String getVoteType() {
			return val;
		}
	}
}
