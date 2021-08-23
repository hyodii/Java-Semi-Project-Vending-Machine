import java.util.Calendar;
import java.util.GregorianCalendar;


class Members					// 회원 VO 클래스
{
	private String pw;					// 비밀번호
	private String name;				// 이름
	private String ssn;					// 주민등록번호  
	private int rentalBook;				// 대출 중인 책 수

	Members(String p, String n, String s)
	{
		pw = p;
		name = n;
		ssn = s;

		rentalBook = 0;		// 기본값 : 대출 0권
	}

	//getter
	public String getPw() {
		return pw;
	}
	public String getName() {
		return name;
	}
	public String getSsn() {
		return ssn;
	}
	public int getRentalBook() {
		return rentalBook;
	}
	

	//setter
	public void setRentalBook(int r) {
		rentalBook = r;
	}	
}


class Books						// 책 VO 클래스
{
	private String author;				// 저자명
	private String publisher;			// 출판사
	private String pubYear;        		// 출판년도
	private String codeNumber;  		// 분류번호
	private String category;          	// 카테고리
	private Calendar storedDate;		// 도서관 입고일자
	private boolean rental;	 			// 현재 대출 여부 (true : 대출 가능 / false : 대출 중)	


	// 생성자
	Books(String a, String p, String y, String cN, String ca, int year, int month, int day)
	{
		author		= a;
		publisher	= p;
		pubYear		= y;
		codeNumber	= cN;
		category	= ca;
		
		storedDate = new GregorianCalendar(year, month, day);
		rental = true;			// 기본값: 대출 가능(true)
	}


	// getter
	public boolean isRental() {
		return rental;
	}
	
	public String getAuthor() {
		return author;
	}
	public String getPublisher() {
		return publisher;
	}
	public String getPubYear() {
		return pubYear;
	}
	public String getCodeNumber() {
		return codeNumber;
	}
	public String getCategory() {
		return category;
	}
	public Calendar getStoredDate() {
		return storedDate;	
	}

	// 추가 getter 메소드
	String getStoredDateStr()		
	{
		String result = storedDate.get(Calendar.YEAR) + "/" 
						+ (storedDate.get(Calendar.MONTH) + 1) + "/"
						+ storedDate.get(Calendar.DATE);			// 출력 예) 2019/2/7
		return result;
	}

	String isRentalStr()
	{
		if (rental)
			return "Y";	// 대출 가능
		else 
			return "N"; // 대출 불가능
	}

	// setter
	public void setRental(boolean r) {
		rental = r;		// 현재 대출 여부 (true : 대출 가능 / false : 대출 중)	
	}
}


class RentalInfo
{
	private Calendar rentalDate;				// 대출한 일자
	private Calendar returnDate;				// 반납된 일자
	private String rBook;						// 대출도서
	private String rMem;						// 대출회원
	private int lateFee;						// 연체료
	private boolean returnBook;					// 반납여부 여부(true: 반납완료, false: 대출 중) 

	// 생성자
	RentalInfo(int rentalY, int rentalM, int rentalD, String book, String mem)
	{
		rentalDate = new GregorianCalendar(rentalY, rentalM, rentalD);
		rBook = book;
		rMem = mem;

		lateFee = 0;			// 기본값 : 연체료 0원
		returnBook = false;		// 기본값 : 반납완료
	}

	// getter
	public String getRBook() {
		return rBook;
	}
	public String getRMem() {
		return rMem;
	}
	public int getLateFee() {
		return lateFee;
	}
	public boolean isReturnBook()
	{
		return returnBook;
	}
	public Calendar getRentalDate() 
	{
		return rentalDate;
	}


	// 추가 getter 메소드
	String getRentalDateStr()		
	{
		String result = rentalDate.get(Calendar.YEAR) + "/" 
						+ (rentalDate.get(Calendar.MONTH)+1) + "/"
						+ rentalDate.get(Calendar.DATE);			// 출력 예) 2019/2/7
		return result;
	}

	String getReturnDateStr()		
	{
		String result = returnDate.get(Calendar.YEAR) + "/" 
						+ (returnDate.get(Calendar.MONTH)+1) + "/"
						+ returnDate.get(Calendar.DATE);
		return result;
	}

	// setter
	public void setReturnDate(int returnY, int returnM, int returnD) {
		this.returnDate	= new GregorianCalendar(returnY, returnM, returnD);
	
	}
	public void setLateFee(int f) {
		lateFee = f;
	}
	public void setReturnBook(boolean r) {
		returnBook = r;
	}

}

class Wish
{
   private String wTitle;		//도서명
   private String wAuthor;		//저자명
   private String wMem;         //신청자명
   private Calendar reqDate;    //신청일자
   private int request;         //신청상태(1:신청완료, 2:입고완료, 3:신청거부)

   // 생성자
   Wish(String wT, String wA, String wM, int year, int month, int day)
   {
      this.wTitle  = wT;
      this.wAuthor = wA;
      this.wMem = wM;
      this.reqDate = new GregorianCalendar(year, month, day);

      this.request = 1;   // 기본값 : 신청완료
   }

   // getter
   public int getRequest() {
      return request;
   }
   public String getWTitle() {
      return wTitle;
   }
   public String getWAuthor() {
      return wAuthor;
   }
   public String getWMem() {
      return wMem;
   }
   public Calendar getReqDate() {
      return reqDate;   
   }

   // 추가 getter 메소드
   String getReqDateStr()      
   {
      String result = reqDate.get(Calendar.YEAR) + "/" 
                  + ( reqDate.get(Calendar.MONTH)+1 ) + "/"
                  + reqDate.get(Calendar.DATE);         // 출력 예) 2019/2/7
      return result;
   }

	String getRequestStr()
	{
		switch (request)
		{
			case 1: return "신청완료";         
			case 2: return "입고완료";        
			case 3: return "신청거부";
			default: return "-";
		}
	}

   // setter
   public void setRequest(int re) {
      request = re;
   }
}