import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Enumeration;
import java.util.ListIterator;
import java.util.Arrays;

// UserSystem클래스 메소드 취합 완료~!

class UserSystem extends LibCommon
{
	// 클래스 인스턴스, 변수 선언
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static String nowId;	//-- 현재 로그인 되어 있는 계정
	static String con;		//-- 유저 입력 컨트롤 값
	static boolean on;		//-- 이용자모드 사용 여부 확인(true: 사용, false: 이용자모드 사용 종료)
	static boolean login;	//-- 로그인 여부 확인(true: 로그인, false: 로그인 X)


	static void onSystem() throws IOException
	{	
		on = true;
		login = false;

		while (on && !login)
		{
			memCheck();					// on → true  : 관리자 로그인 실패 → 이용자 모드 계속 사용해야 함
										//	  → false : 관리자 로그인 성공 → 이용자 모드 종료 가능!

										// login → true : memCheck() 에서 로그인 성공
			// 이용자 모드 실행
			while (login)
			{
				menuDisp();
				menuSelect();
				menuRun();				// 로그아웃 실행 → login = false;
			}	
		}
	}
	

	// ========================== 로그인 관련 메소드 ==========================

	// 회원 확인 메소드
	static void memCheck() throws IOException
	{
		System.out.println();
		System.out.println("■ 회원이십니까? (Y/N)");
		System.out.println("(관리자 모드: A)");

		do
		{
			System.out.print(">> ");
			con = br.readLine().toUpperCase();
			
			if (!(con.equals("Y") || con.equals("N") || con.equals("A")))
				System.out.println("잘못 입력하셨습니다.");
		}
		while (!(con.equals("Y") || con.equals("N") || con.equals("A")));

		switch(con)
		{
			case "Y" : login();	break;
			case "N" : join();	break;
			case "A" : on = !AdSystem.login();	break;	
						// AdSystem.login() → 관리자 모드 로그인에 성공한 경우 true 반환하는 메소드
						// on → true  : 관리자 로그인 실패 → 이용자 모드 계속 사용해야 함
						//	  → false : 관리자 로그인 성공 → 이용자 모드 종료 가능!
		}												
	}


	// 유저 로그인
	static void login() throws IOException
	{
		String tempId, tempPw;	//-- 사용자로부터 입력받은 값
		String rightPw;			//-- 옳은 PW를 저장할 변수

		System.out.println();
		System.out.println("<< 이용자 로그인 >>");

		// 아이디 확인
		do
		{
			System.out.print("■ 아이디 : ");
			tempId = br.readLine();

			if (!Library.memList.containsKey(tempId))
			{
				System.out.println("아이디가 존재하지 않습니다.");
				
				do
				{
					System.out.println("(다시 로그인: Y, 이전 화면으로: N");					
					System.out.print(">> ");	
					con = br.readLine().toUpperCase();
					if ( !(con.equals("Y") || con.equals("N")) )
						System.out.println("잘못 입력하셨습니다.");
				}
				while (!(con.equals("Y") || con.equals("N")));

				if (con.equals("N"))
					return;
			}
		}
		while (!Library.memList.containsKey(tempId));
		

		// 패스워드 확인
		do
		{
			System.out.print("■ 비밀번호 : ");
			tempPw = br.readLine();

			rightPw= (Library.memList.get(tempId)).getPw();

			if (!tempPw.equals(rightPw))
			{
				System.out.println("비밀번호가 일치하지 않습니다.");

				do
				{
					System.out.println("(다시 로그인: Y, 이전 화면으로: N");	
					System.out.print(">> ");
					con = br.readLine().toUpperCase();
					if ( !(con.equals("Y") || con.equals("N")) )
						System.out.println("잘못 입력하셨습니다.");
				}
				while (!(con.equals("Y") || con.equals("N")));

				if (con.equals("N"))
					return;
			}
		}
		while (!tempPw.equals(rightPw));


		// 로그인 성공
		System.out.println(tempId + "님께서 로그인하였습니다.");
		nowId = tempId;			
		login = true;
	}


	// 회원가입
	static void join() throws IOException
	{		
		System.out.println();
		do
		{			
			System.out.println("회원가입하시겠습니까? (Y/N)");
			System.out.print(">> ");
			con = br.readLine().toUpperCase();
			if ( !(con.equals("Y") || con.equals("N")) )
						System.out.println("잘못 입력하셨습니다.");
		}
		while (!(con.equals("Y") || con.equals("N")));
		
		if (con.equals("Y"))
			makeNewMem();		
	}


	// 새로운 계정을 생성하는 메소드
	private static void makeNewMem() throws IOException
	{
		String newId, newPw, newName, newSsn;		//-- 사용자로부터 입력 받을 정보들

		// 아이디 생성
		System.out.println();
		do
		{			
			System.out.print("■ 아이디 입력 : ");
			newId = br.readLine();

			if (Library.memList.containsKey(newId))
				System.out.println("중복된 아이디입니다!");
		}
		while (Library.memList.containsKey(newId));
		

		// 비밀번호 입력
		System.out.print("■ 비밀번호 입력 : ");
		newPw = br.readLine();


		// 정보 입력
		System.out.println();
		System.out.println("■ 회원 정보 입력");		
		System.out.print("이름 : ");
		newName = br.readLine();

		do
		{
			System.out.print("주민등록번호 : ");
			newSsn = br.readLine();
			if (!checkSsn(newSsn))	// 유효성 검사 메소드
				System.out.println("잘못된 주민등록번호입니다!");
		}
		while (!checkSsn(newSsn));


		// 자료구조에 정보 입력
		Library.memList.put(newId, new Members(newPw, newName, newSsn));		
		System.out.println("\n회원가입 완료!");
	}


	// 주민등록번호 유효성 검사 메소드
	private static boolean checkSsn(String str)
	{
		// 주요 변수 선언
		int[] chk = {2, 3, 4, 5, 6, 7, 0, 8, 9, 2, 3, 4, 5};	// 곱해지는 수		
		int tot = 0;			// 곱셈 연산 후 누적합을 담을 변수
		int su;					// 최종 연산 결과가 담을 변수
		
		// ① 입력된 주민등록번호 자릿수 확인
		if (str.length() != 14)
			return false;

		// ②유효성 검사
		for (int i=0; i<13; i++)
		{
			if (i==6)	// 7번째 자리("-")는 연산 생략
				continue;
			tot += chk[i] * Integer.parseInt(str.substring(i, i+1));
		}

		su = 11 - tot % 11;
		su = su % 10;
		// --==>> 최종 연산 결과는 변수 su 에 담긴 상황이다.

		// 결과 반환
		if (su==Integer.parseInt(str.substring(13)))
			return true;
		else
			return false;
	}



	// ============================= 로그인 후 필요 메소드 =============================



	// 유저 메뉴 디스플레이
	static void menuDisp()
	{
		System.out.println();
		System.out.println("<< 이용자 서비스 선택 >>");
		System.out.println("1. 도서 검색");
		System.out.println("2. 도서 대출");
		System.out.println("3. 도서 반납");
		System.out.println("4. 희망도서 신청");
		System.out.println("5. 마이페이지");
		System.out.println("6. 신규 입고 도서 목록");
		System.out.println("7. 로그아웃");
	}

	// 유저 메뉴 선택
	static void menuSelect() throws IOException
	{
		do
		{	
			System.out.print(">> ");
			con = br.readLine();

			if (!(Integer.parseInt(con) >= 1 && Integer.parseInt(con) <= 7))
				System.out.println("잘못 입력하셨습니다.");
		}
		while (!(Integer.parseInt(con) >= 1 && Integer.parseInt(con) <= 7));		
	}
	

	// 유저 메뉴 실행
	static void menuRun() throws IOException
	{		
		System.out.println(); 
		switch(con)
		{
			case "1" : selectSearchMenu();		break;	
			case "2" : rentalBook();			break;	
			case "3" : returnBook();			break;	
			case "4" : requestBook();			break;	
			case "5" : selectMypage();				break;	
			case "6" : newBookListPrint();		break;	
			case "7" : logout();				break;
		}
	}



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	// 1. 도서검색메뉴
	static void selectSearchMenu() throws IOException
	{
		do
		{
			System.out.println();
			System.out.println("<< 도서 검색 >>");
			System.out.println("검색 방법 선택");
			System.out.println("1. 도서명");
			System.out.println("2. 저자명");
			System.out.println("3. 출판사명");
			System.out.println("4. 카테고리명");
			System.out.println("0. 이용자 서비스 선택으로 이동");
			
			do
			{	
				System.out.print(">> ");
				con = br.readLine();

				if ( !(Integer.parseInt(con) >= 0 && Integer.parseInt(con) <= 4) )
					System.out.println("잘못 입력하셨습니다.");
			}
			while (!(Integer.parseInt(con) >= 0 && Integer.parseInt(con) <= 4) );		
			

			switch(con)
			{
				case "1" : searchTitle();			break;			// 도서명 검색
				case "2" : searchAuthor();			break;			// 저자명 검색
				case "3" : searchPublisher();		break;			// 출판사명 검색
				case "4" : searchCategory();		break;			// 카테고리명 검색
			}
		}
		while ( !con.equals("0")  );


	}

	// 1-1. 도서명 검색
	static void searchTitle() throws IOException 
	{
		System.out.print("도서명 입력: ");
		String title = br.readLine();
		System.out.println();

		if (Library.bookList.containsKey(title))		// 입력받은 도서명의 키값이 존재한다면
		{
			System.out.printf("%10s %10s %10s %10s %10s %10s %10s \n", "도서명", "저자명", "출판사", "출판년도", "분류번호", "카테고리", "대출가능여부");
			System.out.println("=============================================================================================================");
			
			Enumeration e = Library.bookList.keys();		// 북리스트 키 값들 받아오기
			while (e.hasMoreElements())						
			{
				if (title.equals(e.nextElement()))			// 입력받은 도서명과 같을 때
				{
					System.out.printf("%10s %8s %10s %11s %15s %10s %12s \n", title
										, (Library.bookList.get(title)).getAuthor() 
										, (Library.bookList.get(title)).getPublisher()
										, (Library.bookList.get(title)).getPubYear()
										, (Library.bookList.get(title)).getCodeNumber()
										, (Library.bookList.get(title)).getCategory() 
										, (Library.bookList.get(title)).isRentalStr());   
				}	
			}
		}
		else											// 입력받은 도서명의 키값이 존재x
		{
			System.out.println("도서가 존재하지 않습니다.");
			System.out.println("(정확히 입력했는지 확인해주세요.)");
			System.out.println();
		}	
	}

	// 1-2. 저자명 검색
	static void searchAuthor() throws IOException 
	{
		System.out.print("저자명 입력: ");
		String author = br.readLine();
		System.out.println();
		String[] bookAuthors = new String[Library.bookList.size()]; // 저자들
		int i=0;
		
		Enumeration e1 = Library.bookList.keys();
		while (e1.hasMoreElements())
		{
			String key = (String)e1.nextElement();
			Books book1 = Library.bookList.get(key);
			bookAuthors[i++] = book1.getAuthor();			// bookList 저자들을 bookAuthors 배열에 담음
		}
		
		if (Arrays.asList(bookAuthors).contains(author))	// 저자들에 입력한 저자가 존재한다면
		{	
			System.out.printf("%10s %10s %10s %10s %10s %10s %10s \n", "도서명", "저자명", "출판사", "출판년도", "분류번호", "카테고리", "대출가능여부");
			System.out.println("=============================================================================================================");
			Enumeration e2 = Library.bookList.keys();		// 북리스트 키 값들 받아오기
			while (e2.hasMoreElements())						
			{
				
				String title = (String)e2.nextElement();		// 키값 넘어가면서 title에 담기
				Books book2 = Library.bookList.get(title);		// vaulue Books를 book2에 담기(Books타입)
				if (author.equals(book2.getAuthor()))			// 입력한 저자명과 book2의 저자명이 같을 때 
				{
					
					System.out.printf("%10s %8s %10s %11s %15s %10s %12s \n", title
										, (Library.bookList.get(title)).getAuthor() 
										, (Library.bookList.get(title)).getPublisher()
										, (Library.bookList.get(title)).getPubYear()
										, (Library.bookList.get(title)).getCodeNumber()
										, (Library.bookList.get(title)).getCategory() 
										, (Library.bookList.get(title)).isRentalStr());   
				}
			}
		}
		else
		{
			System.out.println("도서가 존재하지 않습니다.");
			System.out.println("(정확히 입력했는지 확인해주세요.)");
			System.out.println();
		}						
	}

	// 1-3. 출판사 검색
	static void searchPublisher() throws IOException	
	{
		System.out.print("출판사 입력: ");
		String publisher = br.readLine();
		System.out.println();
		String[] bookPublishers = new String[Library.bookList.size()]; // 출판사들
		int i=0;
		
		Enumeration e1 = Library.bookList.keys();
		while (e1.hasMoreElements())
		{
			String key = (String)e1.nextElement();
			Books book1 = Library.bookList.get(key);			
			bookPublishers[i++] = book1.getPublisher();			// bookList 출판사들을 bookPublishers 배열에 담음
		}
		
		if (Arrays.asList(bookPublishers).contains(publisher))	// 출판사들에 입력한 출판사가 존재한다면
		{	
			System.out.printf("%10s %10s %10s %10s %10s %10s %10s \n", "도서명", "저자명", "출판사", "출판년도", "분류번호", "카테고리", "대출가능여부");
			System.out.println("=============================================================================================================");
			Enumeration e2 = Library.bookList.keys();		// 북리스트 키 값들 받아오기
			while (e2.hasMoreElements())						
			{
				String title = (String)e2.nextElement();		// 키값 넘어가면서 title에 담기
				Books book2 = Library.bookList.get(title);		// vaulue Books를 book2에 담기(Book타입)
				if (publisher.equals(book2.getPublisher()))			// 입력한 출판사명과 book2의 출판사명이 같을 때 
				{
					
					System.out.printf("%10s %8s %10s %11s %15s %10s %12s \n", title
										, (Library.bookList.get(title)).getAuthor() 
										, (Library.bookList.get(title)).getPublisher()
										, (Library.bookList.get(title)).getPubYear()
										, (Library.bookList.get(title)).getCodeNumber()
										, (Library.bookList.get(title)).getCategory() 
										, (Library.bookList.get(title)).isRentalStr());   
				}
			}
		}
		else
		{
			System.out.println("도서가 존재하지 않습니다.");
			System.out.println("(정확히 입력했는지 확인해주세요.)");
			System.out.println();
		}	
	}


	// 1-4. 카테고리 검색
	static void searchCategory() throws IOException	
	{
		System.out.print("카테고리 입력: ");
		String category = br.readLine();
		System.out.println();
		String[] bookCategorys = new String[Library.bookList.size()]; // 카테고리들
		int i=0;
		
		Enumeration e1 = Library.bookList.keys();
		while (e1.hasMoreElements())
		{
			String key = (String)e1.nextElement();
			Books book1 = Library.bookList.get(key);
			bookCategorys[i++] = book1.getCategory();			// bookList 카테고리들을 bookPublishers 배열에 담음
		}
		
		if (Arrays.asList(bookCategorys).contains(category))	// 카테고리들에 입력한 카테고리가 존재한다면
		{	
			System.out.printf("%10s %10s %10s %10s %10s %10s %10s \n", "도서명", "저자명", "출판사", "출판년도", "분류번호", "카테고리", "대출가능여부");
			System.out.println("=============================================================================================================");
			Enumeration e2 = Library.bookList.keys();		// 북리스트 키 값들 받아오기
			while (e2.hasMoreElements())						
			{
				String title = (String)e2.nextElement();		// 키값 넘어가면서 title에 담기
				Books book2 = Library.bookList.get(title);		// vaulue Books를 book2에 담기(Book타입)
				if (category.equals(book2.getCategory()))			// 입력한 카테고리명과 book2의 카테고리명이 같을 때 
				{
					
					System.out.printf("%10s %8s %10s %11s %15s %10s %12s \n", title
										, (Library.bookList.get(title)).getAuthor() 
										, (Library.bookList.get(title)).getPublisher()
										, (Library.bookList.get(title)).getPubYear()
										, (Library.bookList.get(title)).getCodeNumber()
										, (Library.bookList.get(title)).getCategory() 
										, (Library.bookList.get(title)).isRentalStr());   
				}	
			}
		}
		else
		{
			System.out.println("도서가 존재하지 않습니다.");
			System.out.println("(정확히 입력했는지 확인해주세요.)");
			System.out.println();
		}	
	}


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 2. 도서대출
	static void rentalBook() throws IOException
	{
		System.out.println();
		System.out.println("<< 도서 대출 >>");

		if (overdue(nowId).equals("Y"))			// 연체 여부 "Y" 일 떄
		{
			System.out.println("(" + nowId + ")" + "연체료가 존재합니다.");
			System.out.println("도서 반납 후 대출이 가능합니다.");
			System.out.println();
			do
			{
					System.out.print("지금 반납하시겠습니까?(Y/N) : ");;	
					con = br.readLine().toUpperCase();
					if ( !(con.equals("Y") || con.equals("N")) )
						System.out.println("잘못 입력하셨습니다.");
			}
			while (!(con.equals("Y") || con.equals("N")));

			if (con.equals("Y"))			// 반납할 때
				returnBook();
			else	//if (con.equals("N")) 	// 반납안하기 선택해서 메인메뉴로
			{
				System.out.println("도서 대출을 종료합니다.");
				return;
			}
		}
		else									// 연체 여부 "N" 일 떄
		{
			System.out.print("대출할 도서 : ");
			String title = br.readLine();

			if (Library.bookList.containsKey(title))		// 입력받은 도서명의 키값이 존재한다면
			{
				do
				{
						System.out.print("지금 대출하시겠습니까?(Y/N) : ");
						con = br.readLine().toUpperCase();
						if ( !(con.equals("Y") || con.equals("N")) )
							System.out.println("잘못 입력하셨습니다.");	
				}
				while (!(con.equals("Y") || con.equals("N")));

				System.out.println();

				if (con.equals("Y")) // 대출 진행
				{	
					Members mem = Library.memList.get(nowId);		// 현재 로그인된 아이디의 value

					//mem.getRentalBook() => 현재 로그인된 아이디의 대출중인 책 수
					if (mem.getRentalBook() < Library.RENT_BOOKS)		// 현재 대출중인 책 수가 대출가능 책 수 보다 적을 때	
					{
						Library.bookList.get(title).setRental(false);	// 입력한 책 객체의 대출여부에 false(대출중) 넣어줌

						Calendar today = new GregorianCalendar();			// 현재 날짜 받아옴
						int y1,m1,d1;
						y1 = today.get(Calendar.YEAR);
						m1 = today.get(Calendar.MONTH);
						d1 = today.get(Calendar.DATE);
						Library.rentList.add(new RentalInfo(y1, m1, d1, title, nowId));	// rentList(대출현황)에 대출정보 추가
						mem.setRentalBook(mem.getRentalBook() + 1);		// 현재아이디 객체의 대출한 책 수 갱신

						Calendar cal = new GregorianCalendar();
						int y2,m2,d2;
						cal.add(Calendar.DATE, Library.RENT_DAYS);		// 14일 후의 날짜
						y2 = cal.get(Calendar.YEAR);
						m2 = cal.get(Calendar.MONTH);
						d2 = cal.get(Calendar.DATE);

						System.out.printf("%s 대출이 완료되었습니다.\n", title);
						System.out.printf("대여기간은 (%d-%d-%d)까지(%d일) 입니다.\n", y2, (m2+1), d2, Library.RENT_DAYS);
						System.out.println();
					}
					else	// 현재 대출중인 책 수가 대출가능 책 수와 같으면...!! (초과할 수 없음)
					{
						System.out.println("5권 이상 대출 할 수 없습니다. 반납 후 대출하세요.");
					}
				}
				else	//if (con.equals("N")) //대출진행 안할때
				{
					System.out.println("도서 대출을 종료합니다.");
					return;
				}
					
			}
			else											// 입력받은 도서명의 키값이 존재x
			{
				System.out.println();
				System.out.println("존재하지 않는 도서입니다.");
				
			}
		}
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	// 3. 도서반납
	public static void returnBook() throws IOException
	{
		System.out.println("\n<< 도서 반납 >>");
		int bookNum = 0;						// 반납할 권수 담을 변수
		String[] tempStr;						// 권수 만큼 사용자가 입력하는 도서명을 담아둘 배열
		Members ob = Library.memList.get(nowId);		// nowId의 Members객체 가져오기

		System.out.print("반납 권수 : ");
		bookNum = Integer.parseInt(br.readLine());
		if(bookNum ==0)
		{
			System.out.println("[도서 반납 종료]");
			return;
		}
		else if (bookNum > ob.getRentalBook())
		{
			System.out.println(nowId + "님이 반납할 도서는 " + bookNum + "권 미만입니다.");
			return;
		}
		else if (ob.getRentalBook() == 0)
		{
			System.out.println("대출중인 책이 없습니다.");
			return;
		}

		tempStr = new String[bookNum];			// 권수만큼 배열방 생성
		for (int i=0; i<bookNum ; i++)			// 도서명 입력받아 배열방에 담기
		{
			System.out.printf("도서명(%d) : ", i+1 );
			tempStr[i] = br.readLine();
		}
		System.out.println();
		
		int n=0;
		for (int i=0; i<bookNum ; i++)
		{
			for ( RentalInfo e : Library.rentList )			
			{	
				if ( ( (e.getRBook()).equals(tempStr[i]) ) && ( (e.getRMem()).equals(nowId) ) && (!e.isReturnBook()) )
					n++;														// ↑대출정보리스트를 돌면서 회원과 도서가 일치하고 동시에	
			}																	// 반납일자가 갱신되지 않은 즉, 반납되지 않은 도서가 있다면
		}																		// n++;
		
		if (n < bookNum)				// 한권이라도 일치하는 정보 못찾으면.. 불가ㅠ
		{
			System.out.println("[일치하는 대출 정보 조회 불가]");	
			return;
		}			
		
		//----------------------------- 여기까지 메소드가 종료되지 않았다면 입력받은 반납할 도서가 모두 존재하는 것..-------------------------
		
		
		
		if ( overdue(nowId).equals("N") )									// 연체여부 x
		{
			ob.setRentalBook( ob.getRentalBook() - bookNum );			// nowId의 Members객체의 대출 중인 권수를 반납한 권수만큼 줄여주기
			
			updateRentalInfo(tempStr);									// 반납한 도서들의 대출 정보 객체를 업데이트
			
			System.out.println("반납이 완료되었습니다.");
			
		}
		else											// 연체여부ㅇ						
		{
			System.out.println("연체료가 존재합니다.");
			int k =0;				// for 문을 위한 루프변수
			int lateFeeTot = 0;		// 각 책의 연체료를 더할 누적합 변수 => 얼마내야하는지 사용자에게 제시하기 위함

			for ( RentalInfo e : Library.rentList )
			{
				
				if ( ( (e.getRBook()).equals(tempStr[k]) ) && ( (e.getRMem()).equals(nowId) ) &&  (!e.isReturnBook()) )
				{
					System.out.printf("▶ %s(%d일 연체)%,8d원\n", tempStr[k], calLateDays(e), calLateFee(e) );
					lateFeeTot += calLateFee(e);
					k++;
				}
				if(k==bookNum)
					break;
			}

			System.out.printf("=> %,d 원\n", lateFeeTot);	

			do
			{
				System.out.print("연체료 납부(Y/N) : ");
				con = br.readLine().toUpperCase();
				if ( (!con.equals("Y")) && (!con.equals("N")) )
					System.out.println("잘못 입력하였습니다.");
			}
			while ( (!con.equals("Y")) && (!con.equals("N")) );
			
			System.out.println();
			if (con.equals("Y"))
			{
				System.out.printf("》연체 금액 : %d\n", lateFeeTot);
				System.out.println("[지불할 금액 입력(권종별 개수)]");

				int[] kindsOfMoney = {10000, 5000, 1000, 500, 100};			// 권종별 배열 생성	
				int tempM[] = new int[5];									// 권종별 입력 받을 개수 배열								
				int tempTot = 0;											// 입력받은 값의 총합담을 변수

				for (int i=0; i<kindsOfMoney.length ; i++)					// 우선 권종별 입력을 받음(입력만)	
				{
					System.out.printf("%,d원 : ", kindsOfMoney[i]);
					tempM[i] = Integer.parseInt(br.readLine());
					tempTot += ( tempM[i]*kindsOfMoney[i] );
				}
				System.out.println("===========================");
				System.out.printf("지불한 금액 : %,d\n", tempTot );
				System.out.println();

				if(tempTot >= lateFeeTot)									// 권종별 입력한 값을 더했을 때 내야하는 연체료와 같거나 크다면
				{
					if (tempTot-lateFeeTot == 0)							// 같으면 거스름돈 줄 필요x
					{
						for (int i=0; i<5; i++)									// Library.money 배열에 입력받은 권종별 개수 업데이트
							Library.money[i] += tempM[i];

						ob.setRentalBook( ob.getRentalBook() - bookNum );	// nowId의 Members객체의 대출 중인 권수를 반납한 권수만큼 줄여주기
						updateRentalInfo(tempStr);							// 반납한 도서들의 대출 정보 객체를 업데이트	
					}
					else													// 받은 돈이 더 많으면 거스름돈 줄 필요ㅇ
					{
						String result = returnChange(tempTot-lateFeeTot);
						if (result.equals("거스름돈 : " + (tempTot-lateFeeTot) + "원") )		// 거스름돈이 부족하지 않다면
						{
							for (int i=0; i<5; i++)									// Library.money 배열에 입력받은 권종별 개수 업데이트
								Library.money[i] += tempM[i];

							System.out.println(result + "\n");
							ob.setRentalBook( ob.getRentalBook() - bookNum );	// nowId의 Members객체의 대출 중인 권수를 반납한 권수만큼 줄여주기
							updateRentalInfo(tempStr);							// 반납한 도서들의 대출 정보 객체를 업데이트	
						}
						else if (result.equals("잔돈이 부족합니다.") )				// 거스름돈이 부족하다면
						{
							System.out.println(result + "\n도서 반납에 실패하였습니다.\n(관리자에게 문의바람)");
							return;													// 실패했다고 안내 후 메소드 종료
						}
					}			
					System.out.println("연체료 납부 및 도서 반납이 모두 완료되었습니다.");	
				}
				else															// 권종별 입력한 값을 더했을 때 내야하는 연체료보다 작다면
				{	
					System.out.println("입력한 금액이 연체료보다 부족해 납부 실패하였습니다.");		// 금액 부족으로 인한 실패 안내 후 메소드 종료
					return;
				}
			}
			else if (con.equals("N"))
			{
				System.out.println("[연체료 납부 거부]\n도서 반납을 종료합니다.");
			}
			
			System.out.println();
		}
			
	}


	// 도서배열(회원이 반납하려고 가져온 도서 개수만큼의 배열방을 가짐)을 받아 
	// 대출 정보 객체를 업데이트 해주고
	// 각 도서들을 대출가능 상태로 업데이트 해주는 메소드
	private static void updateRentalInfo(String[] tempStr)
	{
		Calendar rightNow = Calendar.getInstance();
		int y = rightNow.get(Calendar.YEAR);
		int m = rightNow.get(Calendar.MONTH);
		int d = rightNow.get(Calendar.DATE);

		for (int i=0; i< tempStr.length ; i++)							// 각 도서객체의 상태 true대출가능으로 변경
			Library.bookList.get(tempStr[i]).setRental(true);

		int i=0;		// for 문을 위한 루프변수 - 도서 권수 만큼 객체를 업데이트 해줘야함.
		for ( RentalInfo e : Library.rentList )
		{
			if (i==tempStr.length)
				break;
			if ( ( (e.getRBook()).equals(tempStr[i]) ) && ( (e.getRMem()).equals(nowId) )  && (!e.isReturnBook()) )	
			{																			// ↑ 대출이력에서 회원과 도서가 일치하면서
				System.out.println(tempStr[i]);											// 반납일자가 갱신되지 않은 즉, 반납되지 않은!
				e.setReturnDate(y, m, d);			// 반납된 일자 1년1월1일 -> 당일 날짜로
				e.setReturnBook(true);				// 반납여부 -> true
				e.setLateFee(calLateFee(e) );		// 연체료
				i++;
			}
		}
	}

	// 거스름돈 계산
	private static String returnChange(int change)
	{
		String result ="";
		int[] kindsOfMoney = {10000, 5000, 1000, 500, 100};	
		int tempM[] = new int[5];							// 거스름돈을 주기위해 권종별로 필요한 개수 담을 배열
		int tempChange = change;							// 밑에서 changd가 필요하므로 tempChange에 담아서 사용

		for (int i=0; i<5 ; i++)							// 기계에 거스름돈으로 줘야할 잔돈 각 권종별로(개수) 확인
		{
			int temp = tempChange / kindsOfMoney[i];		// 몫
			if (temp == 0)
			{
				tempM[i] = 0;
			}
			else if(temp > 0)
			{
				if (Library.money[i] >=temp)
				{
					tempM[i] = temp;
					tempChange -= (kindsOfMoney[i] * temp);
				}
				else
					tempM[i] = 0;
			}
		}

		int tempSum=0;	
		for (int i=0; i<5 ; i++)							
			tempSum += ( kindsOfMoney[i] * tempM[i] );		

		if (tempSum == change)								// 기계에 잔돈이 한 권종도 부족하지 않다면
		{
			for (int i=0; i<5 ; i++)						// 기계가 가지고있는 권종별 잔돈 갯수를 조정하여(빼서) 거스름돈 처리
			{
				Library.money[i] -= tempM[i];
			}
			result = String.format("거스름돈 : %,d원", change);
		}
		else
			result = "잔돈이 부족합니다.";		// 기계에 잔돈이 한 권종이라도 부족하다면 거스름돈 처리x 

		return result;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	// 4. 희망도서 신청
	static void requestBook() throws IOException
	{
		//책 등록 입력값 받기
		System.out.println("<<희망 도서 신청>>");
		System.out.print("도서명 : ");      
		String title = br.readLine();      
		System.out.print("저자명 : ");      
		String author = br.readLine();
		System.out.println();

		Members mem = Library.memList.get(nowId);   // 현재 아이디를 키값으로 하는 Value(Members 타입) 가져옴
		String name = mem.getName();				// 신청자명 (현재 로그인된 회원의 이름)

		Calendar cal = Calendar.getInstance();
		int y,m,d;      // 현재 년 월 일
		y = cal.get(Calendar.YEAR);      //년
		m = cal.get(Calendar.MONTH);   //월   (+1) => 이거 안해줘도 돼요! 출력할때만 해주면 됨!
		d = cal.get(Calendar.DATE);      //일 

		if (Library.bookList.containsKey(title))		// 신청하려는 도서가 이미 존재하면    
		{
			System.out.println("이미 존재하는 도서입니다!");
			return;
		}
		else											// 신청하려는 도서가 존재x
		{    
			do
			{
				System.out.printf("%s 신청하시겠습니까?(Y/N) : ",title);
				con = br.readLine().toUpperCase();
				if ( !(con.equals("Y") || con.equals("N")) )
					System.out.println("잘못 입력하셨습니다.");	
			}
			while (!(con.equals("Y") || con.equals("N")));

			if(con.equals("Y"))
			{
				Library.wishList.add(new Wish(title, author, name, y, m, d));   //wishList자료구조
				System.out.println("신청이 완료 되었습니다.");
			}
			else
				return;

		}
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	// 5. 마이페이지
	static void selectMypage() throws IOException
	{
		do
		{
			System.out.println();
			System.out.println("<< 마이페이지 >>");
			System.out.println("1. 내 정보");
			System.out.println("2. 나의 대출 현황");
			System.out.println("3. 나의 희망 도서 신청 현황");
			System.out.println("0. 이용자 서비스 선택으로 이동");
	   
			do
			{
			System.out.println();
			System.out.print(">> ");
		  
			con = br.readLine();

				if (!(Integer.parseInt(con) >= 0 && Integer.parseInt(con) <= 3) )
					System.out.println("잘못 입력하셨습니다.");
			}
			while (!(Integer.parseInt(con) >= 0 && Integer.parseInt(con) <= 3) ); 

			switch(con)
			{
				case "1" : myInfo();				break;		// 내 정보
				case "2" : myRentalStatus();		break;		// 나의 대출 현황
				case "3" : myWishBookStatus();		break;		// 나의 희망 도서 신청 현황
			}
		}
		while ( !con.equals("0") );


	}

	
	// 5-1. 내 정보
	static void myInfo() throws IOException  
	{      
		System.out.println();
		System.out.println("<< 내 정보 >>");
      
		Enumeration e = Library.memList.keys();
      
		//현재아이디와 memList의 key(id)가 일치하는지 확인하고 아이디, 이름, 주민번호 밸류값을 뽑는다.
		while (e.hasMoreElements())
		{
			if ( nowId.equals(e.nextElement()) )   //현재아이디랑 멤버아이디의 같은 키값일 떄
			{
				System.out.print("▶ 아이디 : " + nowId);    
				System.out.print("\n▶ 이름 : " + Library.memList.get(nowId).getName());
				System.out.print("\n▶ 주민번호 : " + Library.memList.get(nowId).getSsn());
				System.out.println();
			}
		}
	}


	// 5-2. 나의 대출 현황
	static void myRentalStatus() throws IOException
	{
		int c = 0; 
		ListIterator<RentalInfo> r = Library.rentList.listIterator();	
		while (r.hasNext())		
		{
			RentalInfo ren = r.next();	
			if( (nowId.equals(ren.getRMem())) && (!ren.isReturnBook()) )	// 내이름 + 반납되지 않은 책 -> 권수
				c++;
		}
		
		System.out.println();
		System.out.println("<< 나의 대출 현황 >>");
		System.out.println();
		System.out.print("▶ 대출 권수 : " + c);
		System.out.println("\n▶ 대출한 도서\n");
		System.out.println("    도서명      대출 일자       남은 기한         연체여부      연체일수      연체료");
		System.out.println("======================================================================================");
		
		String [] ids = new String [Library.rentList.size()];
		int i =0;
		
		// ids 배열에 회원별 멤버아이디를 담는 중
		ListIterator<RentalInfo> r1 = Library.rentList.listIterator();
		while (r1.hasNext())
		{
			RentalInfo ren2 = r1.next();
			ids[i++] = ren2.getRMem();
		}

		if (Arrays.asList(ids).contains(nowId))	// ids라는 배열을 ArrayList로 바꿔 반환하는 메서드
		{		
			ListIterator<RentalInfo> r2 = Library.rentList.listIterator();
			while (r2.hasNext())	//해당 이터레이션(iteration)이 다음 요소를 가지고 있으면 true를 반환하고, 더 이상 다음 요소를 가지고 있지 않으면 false를 반환함	
			{
				RentalInfo ren = r2.next();	//이터레이션(iteration)의 다음 요소를 반환함.
				if(( nowId.equals(ren.getRMem()) ) && ( !ren.isReturnBook() ))	// 회원별 멤버아이디가 현재아이디와 같고 반납이 아직안되었을때 출력
				{	
					Calendar cal = new GregorianCalendar();       
					int y = ren.getRentalDate().get(Calendar.YEAR);
					int m = ren.getRentalDate().get(Calendar.MONTH);
					int d = ren.getRentalDate().get(Calendar.DATE);
					cal.set(y,m,d);
					cal.add(Calendar.DATE, Library.RENT_DAYS);	//-- 대출 날짜  + 14

					// ○ 남은 반납 기한 계산
					Calendar today = new GregorianCalendar();      //-- 오늘     
					long tempR = today.getTimeInMillis() - cal.getTimeInMillis();	// 오늘 - 반납예정일
					if (tempR<=0)	//반납 예정일이 미래일 때 값이 마이너스 나옴
					{
						long sec = (cal.getTimeInMillis() - today.getTimeInMillis())/ 1000;   // '반납예정일(미래) - 오늘' 의 초

						// 입고날짜와 현재날짜의 차이를 일 단위로 변환
						// ※ 1일 = 24(시간) * 60(분) * 60(초)
						//     따라서, sec 값에서 24*60*60 한 값을 나누면 초 에서 일로 변환 가능
						long days = sec / (24*60*60);
						int result = (int)days;		//-- 남은 기한

						System.out.printf("%8s %10s %10d일 %8s %8d %9d\n",ren.getRBook(), ren.getRentalDateStr()
															,result, overdue(nowId)
															, calLateDays(ren), calLateFee(ren));
					}
					else if(tempR>0)	// 반납 예정일이 이미 지나갔을 때 - 연체발생
					{
						System.out.printf("%8s %10s %10d일 %8s %8d %9d\n",ren.getRBook(), ren.getRentalDateStr(),
															0, overdue(nowId)
															, calLateDays(ren), calLateFee(ren));
					}
				}
			}
		}
		else
			System.out.println("대출한 이력이 없습니다.");

	}


	// 5-3. 나의 희망 도서 신청 현황
	static void myWishBookStatus() throws IOException
	{
		Members mem = Library.memList.get(nowId);	// Menbers 타입 mem에 현재 아이디를 담는다   
		String name = mem.getName();				// String name 에 현재 아이디의 회원이름(value값안에)을 담는다
		String [] names = new String [Library.wishList.size()];
		int i = 0;

		System.out.println();
		System.out.println("<< 나의 희망 도서 신청 현황 >>");
		System.out.println();

		ListIterator<Wish> li2 = Library.wishList.listIterator();
		while (li2.hasNext())
		{
			Wish wish2 = li2.next();		// wish에 있는 다음 요소를 반환
			names [i++] = wish2.getWMem();	// 신청이름들이 들어옴
		}

		if (Arrays.asList(names).contains(name))  // 문자열이 포함되어있는지 여부  contains와 같은 것 
		{
			System.out.printf("%10s%10s%10s%10s", "도서명", "저자명", "신청 일자", "신청 상태");
			System.out.println("\n===========================================================");
         
			ListIterator<Wish> li = Library.wishList.listIterator(); 
			while (li.hasNext())
			{
				Wish wish = li.next(); 
				if (wish.getWMem().equals(name))  //위시에서 신청자명만 가져와서 신청자명이랑 현재로그인된회원의 이름이 같을때
				{
					System.out.printf("%7s %5s %12s %12s\n"
										, wish.getWTitle(), wish.getWAuthor()                                    
										, wish.getReqDateStr(), wish.getRequestStr());  
				}
			}
		}
		else
			System.out.println("신청하신 희망도서가 없습니다.");

		System.out.println();
	}



/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	

	// 로그 아웃
	static void logout()
	{
		System.out.println("로그아웃합니다.");
		login = false;
	}

}
