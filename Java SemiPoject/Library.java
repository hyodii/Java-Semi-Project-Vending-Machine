import java.util.Hashtable;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;


public class Library
{	
	// final 변수 선언
	static final int RENT_BOOKS = 5;		// 대여 가능한 책의 권수: 5권
	static final int RENT_DAYS = 14;		// 대출 기간: 14일
	static final int LATE_FEE = 300;		// 1일당 연체료: 300원
	
	// 자료구조 생성
	public static Hashtable<String, Members> memList = new Hashtable<String, Members>();
	public static Hashtable<String, Books> bookList = new Hashtable<String, Books>();
	public static ArrayList<RentalInfo> rentList = new ArrayList<RentalInfo>();
	public static ArrayList<Wish> wishList = new ArrayList<Wish>();

	// 배열 생성
	public static int[] money = new int[5];		//-- 순서대로 1만원, 5천원, 1천원, 5백원, 1백원 의 갯수
	
	// 테스트 자료 입력
	public static void inputTestData()
	{
		// memList 테스트 값
		memList.put("qwer1234", new Members("1234", "김진희", "990506-2055511")); // 연체O
		memList.put("zxc1111", new Members("1111", "정미화", "950201-2033211")); // 연체O
		memList.put("sdf2222", new Members("2222", "최수지", "961224-2012342")); // 연체X
		memList.put("asdf5678", new Members("5678", "권순모", "971007-1055522")); // 2연체X

		// bookList 테스트 값
		bookList.put("언어의 온도", new Books("이기주", "말글터", "2016", "가-46-13", "에세이", 2017, 10, 2));			//구간대출O 
		bookList.put("마음의 주인", new Books("이기주", "말글터", "2014", "하-12-32", "에세이", 2015, 5, 11));			//구간대출O
		bookList.put("완전한 행복", new Books("정유정", "은행나무", "2013", "아-3-25", "소설", 2018, 8, 15));			//구간대출O
		bookList.put("모모", new Books("미하엘 엔데", "비룡소", "1999", "카-5-13", "소설", 2002, 11, 23));				//구간대출O 
		bookList.put("연금술사", new Books("파울로 코엘료", "문학동네 ", "2001", "바-2-11", "소설", 2003, 3, 20));		//구간대출X  
		bookList.put("28", new Books("정유정", "은행나무", "2011", "다-5-44", "소설", 2021, 7, 2));						//신간대출O
		bookList.put("7년의 밤", new Books("정유정", "은행나무", "2013", "사-5-28", "소설", 2021, 6, 30));				//신간대출O 
		bookList.put("조국의 시간", new Books("조국", "한길사", "2021", "라-55-22", "정치", 2021, 6, 29));              //신간대출O 
		bookList.put("노인과 바다", new Books("김욱동", "민음사", "2012", "파-12-3", "문학", 2021, 7, 21));				//신간대출X
		bookList.put("이기적 유전자", new Books("리처드 도킨스", "을유문화사", "2018", "차-5-1", "과학", 2021, 7, 1));  //신간대출X 
		

        // rentList 테스트 값 [반납 이력 존재하는 책들]
		rentList.add(new RentalInfo(2004, 5, 18, "모모", "zxc1111"));				
		rentList.add(new RentalInfo(2017, 4, 21, "마음의 주인", "qwer1234"));		
		rentList.add(new RentalInfo(2018, 5, 4, "언어의 온도", "qwer1234"));	
		rentList.add(new RentalInfo(2021, 6, 31, "조국의 시간", "sdf2222"));			
		rentList.add(new RentalInfo(2021, 7, 3, "28", "zxc1111"));					
		rentList.add(new RentalInfo(2021, 7, 18, "7년의 밤", "asdf5678"));			
	
		
		// 대출여부 (과거 대출중)
		bookList.get("모모").setRental(false);	
		memList.get("zxc1111").setRentalBook(memList.get("zxc1111").getRentalBook() + 1); 
		bookList.get("마음의 주인").setRental(false);
		memList.get("qwer1234").setRentalBook(memList.get("qwer1234").getRentalBook() + 1); 
		bookList.get("언어의 온도").setRental(false);	// 대출중인 도서로 변경
		memList.get("qwer1234").setRentalBook(memList.get("qwer1234").getRentalBook() + 1); // 대출중인 책 수 +1
		bookList.get("조국의 시간").setRental(false);	
		memList.get("sdf2222").setRentalBook(memList.get("sdf2222").getRentalBook() + 1); 
		bookList.get("28").setRental(false);	
		memList.get("zxc1111").setRentalBook(memList.get("zxc1111").getRentalBook() + 1); 
		bookList.get("7년의 밤").setRental(false);	
		memList.get("asdf5678").setRentalBook(memList.get("asdf5678").getRentalBook() + 1); 

		// 대출한 것 중에  반납
		// 책 제목      대출일자        반납일자      연체
		// 모모		     2004/6/18      2004/6/24      N		// 구간	
		// 마음의 주인   2017/5/21      2017/7/15      Y
        // 언어의 온도   2018/6/4       2018/8/20      Y			
		// 조국의 시간   2021/7/31      2021/8/20      Y		// 신간
		// 28			 2021/8/3       2021/8/18	   Y		
        // 7년의 밤	     2021/8/18      2021/8/19      N
		Iterator<RentalInfo> i = rentList.iterator();
		while (i.hasNext())
		{
			RentalInfo rent = i.next();
			String title = rent.getRBook();			// 대출중인 책이름

			if ("모모".equals(title))		// 연체 x
			{
				// 첫 번째 반납
				rent.setReturnDate(2004, 5, 24);	// 반납한 일자
				rent.setReturnBook(true);			// 반납 o
				
				
				rent.setLateFee(0);				// 연체료 납부(연체없으니 0원 셋팅)

				String id = rent.getRMem();			// 반납한 회원 아이디
				Members mem = memList.get(id);		// 반납한 회원 정보
				mem.setRentalBook(mem.getRentalBook() - 1);	// 기존 대출중인 책 수 -1

				Books book = bookList.get(title);
				book.setRental(true);			// 반납되서 대출가능으로 셋팅	
												
				
			}

			if ("마음의 주인".equals(title))		// 연체 o
			{
				rent.setReturnDate(2017, 6, 15);	// 반납한 일자
				rent.setReturnBook(true);			// 반납 o
				

				Calendar cal = Calendar.getInstance();      //-- 반납 예정일을 담을 변수 선언

				int y = rent.getRentalDate().get(Calendar.YEAR);
				int m = rent.getRentalDate().get(Calendar.MONTH);
				int d = rent.getRentalDate().get(Calendar.DATE);
				cal.set(y,m,d);								 // 대출한 날짜 셋팅   
				cal.add(Calendar.DATE, Library.RENT_DAYS);   // 대출한 날짜 + 대출기한 = 반납 예정일

				Calendar cal2 = new GregorianCalendar(2017, 6, 15);		//-- 반납한 일자를 담을 변수 선언

				// 셋팅한 반납일자 - 반납 예정일(연체료가 생기는 날짜)
				long sec = (cal2.getTimeInMillis() - cal.getTimeInMillis())/ 1000;
				
				long days = sec / (24*60*60);
				
				int result = (int)days;

				int fee = result*LATE_FEE;				// 연체일수 * 300(1일 연체료)
				rent.setLateFee(fee);				// 연체료 납부

				String id = rent.getRMem();			// 반납한 회원 아이디
				Members mem = memList.get(id);		// 반납한 회원 정보
				mem.setRentalBook(mem.getRentalBook() - 1);	// 기존 대출중인 책 수 -1

				Books book = bookList.get(title);
				book.setRental(true);			// 반납되서 대출가능으로 셋팅
				
			}

			if ("언어의 온도".equals(title))		// 연체 o
			{
				rent.setReturnDate(2018, 7, 20);	// 반납한 일자
				rent.setReturnBook(true);			// 반납 o
				
				Calendar cal = Calendar.getInstance();      //-- 반납 예정일을 담을 변수 선언

				int y = rent.getRentalDate().get(Calendar.YEAR);
				int m = rent.getRentalDate().get(Calendar.MONTH);
				int d = rent.getRentalDate().get(Calendar.DATE);
				cal.set(y,m,d);								 // 대출한 날짜 셋팅   
				cal.add(Calendar.DATE, Library.RENT_DAYS);   // 대출한 날짜 + 대출기한 = 반납 예정일

				Calendar cal2 = new GregorianCalendar(2018, 7, 20);		//-- 반납한 일자를 담을 변수 선언

				// 셋팅한 반납일자 - 반납 예정일(연체료가 생기는 날짜)
				long sec = (cal2.getTimeInMillis() - cal.getTimeInMillis())/ 1000;
				
				long days = sec / (24*60*60);
				
				int result = (int)days;

				int fee = result*LATE_FEE;				// 연체일수 * 300(1일 연체료)
				rent.setLateFee(fee);				// 연체료 납부

				String id = rent.getRMem();			// 반납한 회원 아이디
				Members mem = memList.get(id);		// 반납한 회원 정보
				mem.setRentalBook(mem.getRentalBook() - 1);	// 기존 대출중인 책 수 -1

				Books book = bookList.get(title);
				book.setRental(true);			// 반납되서 대출가능으로 셋팅
				
			}
			
			if ("조국의 시간".equals(title))		// 연체 x
			{
				rent.setReturnDate(2021, 7, 20);	// 반납한 일자
				rent.setReturnBook(true);			// 반납 o
				
				Calendar cal = Calendar.getInstance();      //-- 반납 예정일을 담을 변수 선언

				int y = rent.getRentalDate().get(Calendar.YEAR);
				int m = rent.getRentalDate().get(Calendar.MONTH);
				int d = rent.getRentalDate().get(Calendar.DATE);
				cal.set(y,m,d);								 // 대출한 날짜 셋팅   
				cal.add(Calendar.DATE, Library.RENT_DAYS);   // 대출한 날짜 + 대출기한 = 반납 예정일

				Calendar cal2 = new GregorianCalendar(2021, 7, 20);		//-- 반납한 일자를 담을 변수 선언

				// 셋팅한 반납일자 - 반납 예정일(연체료가 생기는 날짜)
				long sec = (cal2.getTimeInMillis() - cal.getTimeInMillis())/ 1000;
				
				long days = sec / (24*60*60);
				
				int result = (int)days;

				int fee = result*LATE_FEE;				// 연체일수 * 300(1일 연체료)
				rent.setLateFee(fee);				// 연체료 납부

				String id = rent.getRMem();			// 반납한 회원 아이디
				Members mem = memList.get(id);		// 반납한 회원 정보
				mem.setRentalBook(mem.getRentalBook() - 1);	// 기존 대출중인 책 수 -1

				Books book = bookList.get(title);
				book.setRental(true);			// 반납되서 대출가능으로 셋팅
				
			}

			if ("28".equals(title))		// 연체 o
			{
				
				rent.setReturnDate(2021, 7, 18);	// 반납한 일자
				rent.setReturnBook(true);			// 반납 o
				
				Calendar cal = Calendar.getInstance();      //-- 반납 예정일을 담을 변수 선언

				int y = rent.getRentalDate().get(Calendar.YEAR);
				int m = rent.getRentalDate().get(Calendar.MONTH);
				int d = rent.getRentalDate().get(Calendar.DATE);
				cal.set(y,m,d);								 // 대출한 날짜 셋팅   
				cal.add(Calendar.DATE, Library.RENT_DAYS);   // 대출한 날짜 + 대출기한 = 반납 예정일

				Calendar cal2 = new GregorianCalendar(2021, 7, 18);		//-- 반납한 일자를 담을 변수 선언

				// 셋팅한 반납일자 - 반납 예정일(연체료가 생기는 날짜)
				long sec = (cal2.getTimeInMillis() - cal.getTimeInMillis())/ 1000;
				
				long days = sec / (24*60*60);
				
				int result = (int)days;

				int fee = result*LATE_FEE;				// 연체일수 * 300(1일 연체료)
				rent.setLateFee(fee);				// 연체료 납부

				String id = rent.getRMem();			// 반납한 회원 아이디
				Members mem = memList.get(id);		// 반납한 회원 정보
				mem.setRentalBook(mem.getRentalBook() - 1);	// 기존 대출중인 책 수 -1

				Books book = bookList.get(title);
				book.setRental(true);			// 반납되서 대출가능으로 셋팅

				
			}

			
			if ("7년의 밤".equals(title))		// 연체 x
			{
				//2021.7.18
				rent.setReturnDate(2021, 7, 19);	// 반납한 일자
				rent.setReturnBook(true);			// 반납 o
				
				
				rent.setLateFee(0);				// 연체료 납부(연체없으니 0원 셋팅)

				String id = rent.getRMem();			// 반납한 회원 아이디
				Members mem = memList.get(id);		// 반납한 회원 정보
				mem.setRentalBook(mem.getRentalBook() - 1);	// 기존 대출중인 책 수 -1

				Books book = bookList.get(title);
				book.setRental(true);			// 반납되서 대출가능으로 셋팅
				
			}
				
		}


		// rentList 테스트 값 [현재 대출중인 책]
		rentList.add(new RentalInfo(2021, 3, 2, "모모", "zxc1111"));				//연체O		대출,반납 이력 O 
		rentList.add(new RentalInfo(2021, 4, 26, "완전한 행복", "zxc1111"));		//연체O		대출,반납 이력 x 
		rentList.add(new RentalInfo(2021, 5, 30, "마음의 주인", "qwer1234"));		//연체O		대출,반납 이력 O
 		rentList.add(new RentalInfo(2021, 6, 6, "언어의 온도", "qwer1234"));		//연체O		대출,반납 이력 O	
		rentList.add(new RentalInfo(2021, 7, 21, "28", "sdf2222"));					//연체X		대출,반납 이력 O 
		rentList.add(new RentalInfo(2021, 7, 20, "7년의 밤", "sdf2222"));			//연체X		대출,반납 이력 O 
		rentList.add(new RentalInfo(2021, 7, 22, "조국의 시간", "asdf5678"));		//연체X		대출,반납 이력 O 

		// 대출여부 (현재)
		bookList.get("모모").setRental(false);	
		memList.get("zxc1111").setRentalBook(memList.get("zxc1111").getRentalBook() + 1); 
		bookList.get("완전한 행복").setRental(false);	
		memList.get("zxc1111").setRentalBook(memList.get("zxc1111").getRentalBook() + 1); 
		bookList.get("마음의 주인").setRental(false);
		memList.get("qwer1234").setRentalBook(memList.get("qwer1234").getRentalBook() + 1); 
		bookList.get("언어의 온도").setRental(false);	// 대출중인 도서로 변경
		memList.get("qwer1234").setRentalBook(memList.get("qwer1234").getRentalBook() + 1); // 대출중인 책 수 +1
		bookList.get("28").setRental(false);	
		memList.get("sdf2222").setRentalBook(memList.get("sdf2222").getRentalBook() + 1); 
		bookList.get("7년의 밤").setRental(false);
		memList.get("sdf2222").setRentalBook(memList.get("sdf2222").getRentalBook() + 1); 
		bookList.get("조국의 시간").setRental(false);
		memList.get("asdf5678").setRentalBook(memList.get("asdf5678").getRentalBook() + 1); 
		
		

		// wishList 테스트 값
		wishList.add(new Wish("미래의 부", "이지성", "권순모", 2021, 7, 15));
		wishList.add(new Wish("불편한 편의점", "김호연", "김진희", 2021, 3, 30));
		wishList.add(new Wish("어떻게 말해줘야 할까", "오은영", "정미화", 2019, 12, 7));
	    wishList.add(new Wish("아몬드", "손원평", "최수지", 2020, 10, 18));
		wishList.add(new Wish("요절", "조용훈", "최수지", 2020, 8, 28));
		wishList.add(new Wish("매매의 기술", "박평창", "권순모", 2021, 2, 8));
		wishList.add(new Wish("지구 끝의 온실", "김초엽", "권순모", 2020, 12, 26));
		wishList.add(new Wish("어린이라는 세계", "김소영", "김진희", 2020, 9, 9));

		// 거스름돈 테스트값
		//-- 순서대로 1만원, 5천원, 1천원, 5백원, 1백원 의 갯수
		//    78300원
		money[0] = 1;
		money[1] = 11;
		money[2] = 2;
		money[3] = 22;
		money[4] = 3;
      
	}


	// 메인메소드
	public static void main(String[] args) //throws IOException
	{
		try
		{
			// 테스트 값 입력
			inputTestData();

			// 실행
			AdSystem.onSystem();
		}
		catch (Exception e)
		{
			System.out.println("\n※잘못된 값 발생※");
			System.out.println("프로그램 종료 후 다시 실행하세요");
			System.out.println("printStackTrace.........................");
			e.printStackTrace();
		}

	}

	
}