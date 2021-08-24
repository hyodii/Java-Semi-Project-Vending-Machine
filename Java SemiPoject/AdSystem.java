import java.util.Hashtable;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Calendar;
import java.util.GregorianCalendar;

//  AdSystem클래스 메소드 취합 완료~!

class AdSystem extends LibCommon
{
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	private static final String ADMIN_ID = "admin";
	private static final String ADMIN_PW = "1234";
	
	static String con;		//-- 유저 입력 컨트롤 값


	// 관리자 메뉴 실행
	static void onSystem() throws IOException
	{	
		while(!login());

		while(true)
		{
			menuDisp();
			menuSelect();
			menuRun();
		}
	}


	// 관리자 메뉴 디스플레이
	static void menuDisp()
	{
		System.out.println();
		System.out.println("<< 관리자 서비스 선택 >>");
		System.out.println("1. 도서 대출 현황");
		System.out.println("2. 도서 관리");
		System.out.println("3. 회원정보 조회 및 삭제");
		System.out.println("4. 연체료 관리");
		System.out.println("5. 이용자모드");
		System.out.println("6. 프로그램 종료");
	}


	// 관리자 메뉴 선택
	static public void menuSelect() throws IOException
	{
		do
		{	
			System.out.print(">> ");
			con = br.readLine();

			if (!(Integer.parseInt(con) >= 1 && Integer.parseInt(con) <= 6))
				System.out.println("잘못 입력하셨습니다.");
		}
		while (!(Integer.parseInt(con) >= 1 && Integer.parseInt(con) <= 6));
	}


	// 관리자 메뉴 실행
	static public void menuRun() throws IOException
	{
		System.out.println(); 

		switch(con)
		{
			case "1" : selectInfoMenu();		break;	// 도서 대출 현황
			case "2" : selectAdminMenu();		break;	// 도서 관리
			case "3" : selectMemCon();			break;	// 회원정보 조회 및 삭제
			case "4" : selectLateFee();			break;	// 연체료 관리
			case "5" : userOn();				break;	// 이용자모드
			case "6" : exit();					break;	// 프로그램 종료
		}
	}

	// 관리자 로그인
	static boolean login() throws IOException
	{
		String temp;	//-- 입력받은 값
	
		System.out.println();
		System.out.println("<< 관리자 로그인 >>");

		// 아이디 확인
		do
		{
			System.out.print("■ 아이디 : ");
			temp = br.readLine();

			if (!ADMIN_ID.equals(temp))
				System.out.println("잘못 입력하셨습니다.");
		}
		while (!ADMIN_ID.equals(temp));
		

		// 패스워드 확인
		do
		{
			System.out.print("■ 비밀번호 : ");
			temp = br.readLine();

			if (!ADMIN_PW.equals(temp))
				System.out.println("잘못 입력하셨습니다.");
		}
		while (!ADMIN_PW.equals(temp));


		// 로그인 성공
		System.out.println("관리자로 로그인하였습니다.");
		return true;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	// 1. 도서 대출 현황
	static void selectInfoMenu() throws IOException
	{
		do
		{
			System.out.println("<<도서 대출 현황>>");
			System.out.println("1. 대출 현황 검색");
			System.out.println("2. 연체 대출 현황 조회");
			System.out.println("3. 전체 대출 현황 조회");
			System.out.println("0. 관리자 서비스 선택으로 이동");

			do
			{	
				System.out.print(">> ");
				con = br.readLine();

				if ( Integer.parseInt(con) < 0 && Integer.parseInt(con) >3  )
					System.out.println("잘못 입력하셨습니다.");
			}
			while ( Integer.parseInt(con) < 0 && Integer.parseInt(con) >3);
			
			/*
			boolean on = true;
			switch(con)
			{
				case "1" : selectSearchMenu();	break;	// 대출 현황 검색
				case "2" : overdueStatus();		break;	// 연체 대출 현황 조회
				case "3" : rentalStatus();		break;	// 전체 대출 현황 조회
				case "4" : on = false;			break;	// 관리자 메뉴로 이동 Back!
			}
			return on;
			*/

		
			switch(con)
			{
				case "1" : selectSearchMenu();	break;	// 대출 현황 검색
				case "2" : overdueStatus();		break;	// 연체 대출 현황 조회
				case "3" : rentalStatus();		break;	// 전체 대출 현황 조회	
			}
		}
		while ( !con.equals("0") );
	}


	// 1-1. 대출 현황 검색
	static void selectSearchMenu() throws IOException
	{

		System.out.println("\n<<대출 현황 검색>>");
		System.out.println("1. 도서 제목");
		System.out.println("2. 이용자 ID");
		System.out.println("3. 이전메뉴로");

		do
		{	
			System.out.print(">> ");
			con = br.readLine();

			if (Integer.parseInt(con) < 0 && Integer.parseInt(con) >2)
				System.out.println("잘못 입력하셨습니다.");
		}
		while (Integer.parseInt(con) < 0 && Integer.parseInt(con) >2);
		
		switch(con)
		{
			case "1" : searchBook();	break;	// 도서 제목 검색
			case "2" : searchId();		break;	// 이용자 ID 검색
		}
		System.out.println();
	}


	 // 1-1-1. 도서 제목으로 검색
	static void searchBook() throws IOException
	{
	System.out.println("\n<<도서 제목 검색>>");
	System.out.print("도서 제목 : ");
	String title = br.readLine();

	System.out.println("\n<<도서 대출 현황>>");

	if(Library.bookList.containsKey(title)){            //존재여부
	 Enumeration e = Library.bookList.keys();         //e에 bookList 키들

	 if(Library.bookList.get(title).isRental()){         //대출가능여부
	    System.out.printf("%s 은/는 대출 가능합니다.\n",title);
	    selectSearchMenu();
	 }else{
	    for(int i=Library.rentList.size()-1; i>0; i--){
	       RentalInfo valR = Library.rentList.get(i);

	       if(valR.getRBook().equals(title)){         //rentlist에 그 책이 존재할 때
		  System.out.printf("%s 은/는 현재 대출중입니다.", title);
		  System.out.printf("\n▶이용자 : %s", valR.getRMem());

		  // ○ 반납 예정일 계산                  
		  Calendar cal = new GregorianCalendar();         //-- 반납 예정일을 담을 변수 선언

		  int y = valR.getRentalDate().get(Calendar.YEAR);
		  int m = valR.getRentalDate().get(Calendar.MONTH);
		  int d = valR.getRentalDate().get(Calendar.DATE);
		  cal.set(y,m,d);                           // 대출한 날짜 셋팅   
		  cal.add(Calendar.DATE, Library.RENT_DAYS);      // 대출한 날짜 + 대출기한 = 반납 예정일
						      // Library.RENT_DAYS: final 변수로 선언한 대출기한

		  // ----------- 반납 예정일 계산 완료!


		  // ○ 남은 반납 기한 계산
		  Calendar today = new GregorianCalendar();      //-- 오늘                  
		  long sec = (cal.getTimeInMillis() - today.getTimeInMillis())/ 1000;   // '반납예정일(미래) - 오늘' 의 초

		  // 입고날짜와 현재날짜의 차이를 일 단위로 변환
		  // ※ 1일 = 24(시간) * 60(분) * 60(초)
		  //     따라서, sec 값에서 24*60*60 한 값을 나누면 초 에서 일로 변환 가능
		  long days = sec / (24*60*60);

		  // ○ 출력
		  if(days>=0)
		     System.out.printf("\n▶남은 반납 기한 : %d일\n", days); 
		  else
		     System.out.printf("\n▶%d일 째, 연체 중\n", Math.abs(days));   //절댓값으로 연체 중인 날짜 계산

		  return;
	       }
	    }
	 }
	}else
	 System.out.printf("\n%s 은/는 존재하지 않는 도서입니다.\n", title);  

	}


	// 1-1-2. 이용자 ID로 검색
	static void searchId() throws IOException
	{
		String tempId;   //-- 사용자로부터 입력 받은 값을 담을 변수

		System.out.println("\n<<이용자 ID 조회>>");
		System.out.print("회원 ID: ");
		tempId = br.readLine();

		System.out.println();
		System.out.println("<< 회원 ID 검색 결과 >>");

		if (Library.memList.containsKey(tempId))   // 검색한 ID가 있다면
		{
			// valMem = 검색한 회원 정보
			Members valMem = Library.memList.get(tempId);

			System.out.println("회원 ID : " + tempId);
			System.out.println("이름    : " + valMem.getName());

			System.out.println();
			System.out.println("[대출 중인 책 목록]");
			System.out.printf("%15s \t%3s \t%4s\n", "도서명", "연체", "연체료");
			System.out.println("=======================================");

			int i = 1; //-- 출력할 책의 순서를 담을 변수
			for (int idx=0; idx<Library.rentList.size(); idx++)   // ①
			{
				RentalInfo r = Library.rentList.get(idx);   //-- 이번에 받아온 대출 정보
						
				if (tempId.equals(r.getRMem()) && !r.isReturnBook() )   // ②, ③
					System.out.printf("[%d] %11s \t%3s \t%,4d\n", i++, r.getRBook(), overdue(r), calLateFee(r));
			}
			System.out.println("=======================================");

			System.out.printf("총 미납 연체료: %,d원\n", calLateFee(tempId));
		}
		else
			System.out.println(tempId + "은/는 존재하지 않는 회원입니다.");

		System.out.println();
	}

	
	// 1-2. 연체 대출 현황 조회
	static void overdueStatus() throws IOException
	{
		System.out.println("\n<<연체 대출 현황 조회>>");
		System.out.printf("%12s %7s %7s %5s %5s\n", "도서명", "이용자명", "대출일자", "연체일수", "연체료");
		System.out.println("===================================================================");
		
		
		//출력
		Enumeration e = Library.memList.keys(); 
		//String tempName="";
		while(e.hasMoreElements()) 
		{   
			String keyId = (String)e.nextElement();
			Members valMem = Library.memList.get(keyId);

			for(int i=0; i<Library.rentList.size(); i++){
				RentalInfo valR = Library.rentList.get(i);

				if(valR.getRMem().equals(keyId) && calLateFee(valR) >0 && !valR.isReturnBook() )		//&& !valR.isReturnBook()
					System.out.printf("%12s %7s %7s %5d %5d\n", valR.getRBook(), Library.memList.get(keyId).getName(), 
												valR.getRentalDateStr(), calLateDays(valR), calLateFee(valR));
			}
		}
		System.out.println();

	}
	

	// 1-3. 전체 대출 현황 조회
	static void rentalStatus() throws IOException
	{
		System.out.println("\n<<전체 대출 현황 조회>>");
		System.out.printf("%12s %7s %7s %5s %5s\n", "도서명", "이용자명", "대출일자", "연체여부", "연체일수");
		System.out.println("===================================================================");
		
		
		//출력
		Enumeration e = Library.memList.keys(); 
		String tempName="";
		while(e.hasMoreElements()) 
		{   
			String keyId = (String)e.nextElement();
			Members valMem = Library.memList.get(keyId);

			for(int i=0; i<Library.rentList.size(); i++){
				RentalInfo valR = Library.rentList.get(i);

				if( valR.getRMem().equals(keyId) && !valR.isReturnBook() )			//&& !valR.isReturnBook()
				{
					tempName = Library.memList.get(keyId).getName();
					System.out.printf("%12s %7s %7s %5s %5s\n", valR.getRBook(), tempName, valR.getRentalDateStr(), overdue(keyId), calLateDays(valR));
				}
			}
		}

		System.out.println();

	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	// 2. 도서 관리 선택
	static void selectAdminMenu() throws IOException
	{
		do
		{
			System.out.println("\n<<도서 관리 선택>>");
			System.out.println("1. 도서 추가");
			System.out.println("2. 전체 도서 목록");
			System.out.println("3. 희망 도서 관리");
			System.out.println("4. 신규 입고 도서 목록");
			System.out.println("0. 관리자 서비스 선택으로 이동");

			do
			{	
				System.out.print(">> ");
				con = br.readLine();

				if ( Integer.parseInt(con) < 0 && Integer.parseInt(con) >4)
					System.out.println("잘못 입력하셨습니다.");
			}
			while ( Integer.parseInt(con) < 0 && Integer.parseInt(con) >4);
			
		
			switch(con)
			{
				case "1" : addBook();					break;	// 도서추가
				case "2" : allBookList();				break;	// 전체 도서 목록
				case "3" : wishBookCon();				break;	// 희망 도서 목록
				case "4" : newBookListPrint();			break;	// 신규 입고 도서 목록
			}
		}
		while ( !con.equals("0") );
	
	}

	// 2-1. 도서 추가
	static void addBook() throws IOException
	{	
		String title, a, p, y, cN, ca;
		System.out.print("도서명 : ");
		title = br.readLine();
		System.out.print("저자 : ");
		a = br.readLine();
		System.out.print("출판사 : ");
		p = br.readLine();
		System.out.print("출판년도 : ");
		y = br.readLine();
		System.out.print("분류번호 : ");
		cN = br.readLine();
		System.out.print("카테고리 : ");
		ca = br.readLine();
		Calendar cal = new GregorianCalendar();

		Library.bookList.put(title, new Books(a,p,y,cN,ca, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)) );
		System.out.println("도서 추가 완료!");
	}


	// 2-2. 전체 도서 목록
	static void allBookList() throws IOException
	{
		System.out.println("\n\t\t\t\t[전체 도서 목록]");
		System.out.println("===================================================================================================================");
		System.out.printf("%s %10s %10s %10s %10s %10s %12s %10s\n", "도서명", "저자명", "출판사", "출판년도", "분류번호", "카테고리", "입고일자", "대출여부");
		System.out.println("===================================================================================================================");

		Enumeration e = Library.bookList.keys(); 

		while(e.hasMoreElements()) 
		{   
			String keyTitle = (String)e.nextElement();
			Books valBook = Library.bookList.get(keyTitle);

			System.out.printf("%s %10s %10s %10s %10s %10s %10s %10s\n", keyTitle, valBook.getAuthor(), valBook.getPublisher(), 
								valBook.getPubYear(), valBook.getCodeNumber(), valBook.getCategory(), valBook.getStoredDateStr(), valBook.isRentalStr());
		}   
	}


	// 2-3. 희망 도서 관리
	static void wishBookCon() throws IOException
	{	
		//희망 도서 목록
		System.out.println("\n===================================================================");
		System.out.printf("%12s %10s %5s %10s %5s\n", "도서명", "저자명", "신청자", "신청일자", "신청상태");
		System.out.println("===================================================================");
		
		for(int i=0; i<Library.wishList.size(); i++){
			Wish valW = Library.wishList.get(i);
			System.out.printf("%12s %10s %5s %10s %5s\n",valW.getWTitle(),valW.getWAuthor()
							,valW.getWMem(), valW.getReqDateStr(), valW.getRequestStr());
	}

		//신청거부메뉴
		System.out.println("1. 신청거부");
		System.out.println("2. 이전메뉴로");

		do
		{	
			System.out.print(">> ");
			con = br.readLine();

			if ( Integer.parseInt(con) < 1 && Integer.parseInt(con) >2 )
				System.out.println("잘못 입력하셨습니다.");
		}
		while ( Integer.parseInt(con) < 1 && Integer.parseInt(con) >2);
		
		switch(con)
		{
			case "1" : wishReject();			break;	// 신청거부
			case "2" : return;							// 이전메뉴로
		}
	}
	
	static void wishReject() throws IOException
	{
		System.out.println("\n[희망 도서 신청 거부]");
		System.out.print("도서명 입력 : ");
		String title = br.readLine();

		for(int i=0; i<Library.wishList.size(); i++){
			Wish valW = Library.wishList.get(i);

			if((valW.getWTitle()).equals(title)){
				valW.setRequest(3);
				System.out.printf("\n%s 이/가 신청 거부되었습니다.\n",title);
				selectAdminMenu();
				return;
			}
			else{
				if(i==Library.wishList.size()-1)//{
					System.out.printf("%s 은/는 희망 도서에 존재하지 않습니다.\n",title);
								
				continue;	//wishList 마지막까지 존재하는지 확인하기 위해 i번째 해당 반복문만 탈출
			}
		}
	}


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	// 3. 회원정보 조회 및 삭제
	static void selectMemCon() throws IOException
	{
		do
		{
			// 선택창 디스플레이
			System.out.println("<회원정보 조회 및 삭제>");
			System.out.println("1. 전체 회원 조회"); 
			System.out.println("2. 회원 검색"); 
			System.out.println("3. 회원 삭제"); 
			System.out.println("0. 관리자 서비스 선택으로 이동");
		
			// 선택 받기
			do
			{	
				System.out.print(">> ");
				con = br.readLine();

				if (!(Integer.parseInt(con) >= 0 && Integer.parseInt(con) <= 3))
					System.out.println("잘못 입력하셨습니다.");
			}
			while (!(Integer.parseInt(con) >= 0 && Integer.parseInt(con) <= 3));

			// 실행
			System.out.println(); 

			
			switch(con)
			{
				case "1" : printAllMem();	break;	// 전체 회원 조회
				case "2" : searchMem();		break;	// 회원 검색
				case "3" : removeMem();		break;	// 회원 삭제
			}
		}
		while ( !con.equals("0") );

		
	}


	// 3-1. 전체 회원 조회
	private static void printAllMem()
	{
		System.out.println("\t\t[전체 회원 조회]");
		System.out.println("===================================================================");
		System.out.printf("%7s \t%5s \t%8s \t%3s \t%6s \n", "ID", "이름", "대출도서(권)", "연체", "미납연체료");
		System.out.println("===================================================================");

		Enumeration e = Library.memList.keys();   
		while(e.hasMoreElements()) 
		{	
			String keyId = (String)e.nextElement();
			Members valMem = Library.memList.get(keyId);

			System.out.printf("%7s \t%5s \t%8s \t%3s \t%,6d \n", 
								keyId, valMem.getName(), valMem.getRentalBook(), overdue(keyId), calLateFee(keyId));
		}
		System.out.println("===================================================================");
	}
	
	// 3-2. 회원 검색
	private static void searchMem() throws IOException
	{
		String tempId;	//-- 사용자로부터 입력 받은 값을 담을 변수

		System.out.println("<회원정보 조회>");
		System.out.print("회원 ID: ");
		tempId = br.readLine();

		System.out.println();
		System.out.println("<< 회원 ID 검색 결과 >>");

		if (Library.memList.containsKey(tempId))	// 검색한 ID가 있다면
		{
			// valMem = 검색한 회원 정보
			Members valMem = Library.memList.get(tempId);

			System.out.println("회원 ID : " + tempId);
			System.out.println("이름    : " + valMem.getName());

			// 대출 중인 책 출력
			//	① 대출정보(Library.rentList)를 전체적으로 확인하여,
			//	② 입력 받은 ID(tempId) 와 대출정보 id(rentInfo 의 rMem)가 동일하고, 
			//		대출 중(rentInfo 의 returnBook)인 값을 찾아서 (※ returnBook → true: 반납완료, false: 대출 중)
			//	③ 그 값의 정보를 출력!
			
			System.out.println();
			System.out.println("[대출 중인 책 목록]");
			System.out.printf("%10s \t%3s \t%4s\n", "도서명", "연체", "연체료");
			System.out.println("============================================");

			int i = 1; //-- 출력할 책의 순서를 담을 변수
			for (int idx=0; idx<Library.rentList.size(); idx++)	// ①
			{
				RentalInfo r = Library.rentList.get(idx);	//-- 이번에 받아온 대출 정보
								
				if (tempId.equals(r.getRMem()) && !r.isReturnBook() )	// ②, ③
					System.out.printf("[%d] %9s \t%3s \t%,4d\n", i++, r.getRBook(), overdue(r), calLateFee(r));
			}
			System.out.println("============================================");

			System.out.printf("총 미납 연체료: %,d원\n", calLateFee(tempId));
		}
		else
			System.out.println(tempId + "은/는 존재하지 않는 회원입니다.");

		System.out.println();
	}

	// 3-3. 회원 삭제
	private static void removeMem() throws IOException		
	{
		String temp;

		System.out.println("<회원정보 삭제>");
		System.out.print("회원 ID: ");
		temp = br.readLine();

		System.out.println();
		System.out.println("<< 회원 ID 삭제 결과 >>");

		if (Library.memList.containsKey(temp))	// 검색한 ID가 있다면
		{
			Library.memList.remove(temp);

			System.out.println("회원 " + temp + "을/를 삭제했습니다.");
		}
		else
			System.out.println(temp + "은/는 존재하지 않는 회원입니다.");
		System.out.println();
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	// 4. 연체료 관리
	static void selectLateFee() throws IOException
	{
		do
		{
			// 선택창 디스플레이
			System.out.println("<연체료 관리>");
			System.out.println("1. 총 연체료 조회"); 
			System.out.println("2. 회원별 연체료 이력 조회"); 
			System.out.println("3. 도서별 연체료 이력 조회"); 
			System.out.println("4. 거스름돈 조회 및 입출금"); 
			System.out.println("0. 관리자 서비스 선택으로 이동");
		
			// 명령어 입력받기
			do
			{	
				System.out.print(">> ");
				con = br.readLine();

				if (!(Integer.parseInt(con) >= 0 && Integer.parseInt(con) <= 4) )
					System.out.println("잘못 입력하셨습니다.");
			}
			while (!(Integer.parseInt(con) >= 0 && Integer.parseInt(con) <= 4) );

			// 실행
			System.out.println(); 

			switch(con)
			{
				case "1" : printAllLateFee();		break;	// 총 연체료 조회
				case "2" : printMemLateFee();		break;	// 회원별 연체료 이력 조회
				case "3" : printBookLateFee();		break;	// 도서별 연체료 이력 조회
				case "4" : manageMoney();			break;	// 거스름돈 조회 및 입출금
			}
		}
		while ( !con.equals("0") );
		
	}

	// 4-1. 총 연체료 조회
	static void printAllLateFee()
	{
		// ① rentList 를 전부 체크하면서
		// ② 반납 O 자료 : 총 연체료에 누적합
		// ③ 반납 X 자료 : 미납연체료에 누적합
		// ※ 반납여부: rentInfo 의 returnBook → true: 반납완료, false: 대출 중

		int tot1 = 0; //-- 총 연체료 
		int tot2 = 0; //-- 미납연체료 

		for (int idx=0; idx<Library.rentList.size(); idx++)	// ①
		{
			RentalInfo r = Library.rentList.get(idx);	//-- 이번에 받아온 대출 정보
							
			if (r.isReturnBook())	// ②
				tot1 += r.getLateFee();
			else
				tot2 += calLateFee(r);
		}

		// 출력
		System.out.println("<총 연체료 조회>");
		System.out.printf("총 연체료   : %,d원\n", tot1);
		System.out.printf("미납 연체료 : %,d원\n", tot2);
		System.out.println();
	}

	// 4-2. 회원별 연체료 이력 조회
	static void printMemLateFee()
	{
		// ① memList 를 전체 체크하면서
		// ② calLateFee() 메소드로, 납부/미납 출력

		System.out.println("<회원별 연체료 조회>");
		System.out.printf("%10s \t%5s \t%6s \t%6s\n", "회원ID", "이름", "납부 연체료", "미납 연체료");
		System.out.println("==================================================================");


		Enumeration e = Library.memList.keys();	// 키값 얻어오기
  
		while(e.hasMoreElements())	// ①
		{
			String id = (String)e.nextElement();
			Members m = Library.memList.get(id);

			System.out.printf("%10s \t%5s \t  %,6d \t  %,6d\n", 
								id, m.getName(), calLateFee(id, true), calLateFee(id, false));
		}		
		System.out.println();
	}

	// 4-3. 도서별 연체료 이력 조회
	static void printBookLateFee()
	{
		// ① bookList 를 전체 체크하면서
		// ② calLateFee() 메소드로, 납부/미납 출력

		System.out.println("<도서별 연체료 조회>");
		System.out.printf("%10s \t%5s \t%6s \t%6s\n", "도서명", "저자", "납부 연체료", "미납 연체료");
		System.out.println("==================================================================");


		Enumeration e = Library.bookList.keys();	// 키값 얻어오기
  
		while(e.hasMoreElements())	// ①
		{
			String key = (String)e.nextElement();
			Books m = Library.bookList.get(key);

			System.out.printf("%10s \t%5s \t  %,6d \t  %,6d\n", 
								key, m.getAuthor(), calLateFee(key, true, false), calLateFee(key, false, false));
		}		
		System.out.println();
	}

	
	// 4-4. 거스름돈 조회 및 입출금
	static void manageMoney() throws IOException
	{
		do
		{
			// ○거스름돈 조회
			System.out.println("<거스름돈 조회 및 입출금>");
			System.out.printf("현재 거스름돈은 %,d원 있습니다.\n\n", moneyTot(Library.money));
			
			// money 배열: 순서대로 1만원, 5천원, 1천원, 5백원, 1백원 의 갯수
			System.out.printf("10,000원: %d장\n", Library.money[0]);
			System.out.printf("5,000원: %d장\n", Library.money[1]);
			System.out.printf("1,000원: %d장\n", Library.money[2]);
			System.out.printf("500원: %d개\n", Library.money[3]);
			System.out.printf("100원: %d개\n", Library.money[4]);
			System.out.println("");

			
			// ○ 메뉴 선택창
			System.out.println("1. 거스름돈 입금");
			System.out.println("2. 거스름돈 출금");
			System.out.println("3. 메뉴로");


			// ○ 선택 입력 받기
			do
			{	
				System.out.print(">> ");
				con = br.readLine();

				if (!(Integer.parseInt(con) >= 1 && Integer.parseInt(con) <= 3))
					System.out.println("잘못 입력하셨습니다.");
			}
			while (!(Integer.parseInt(con) >= 1 && Integer.parseInt(con) <= 3));
			
			
			// ○ 실행
			switch (con)
			{
				case "1" : inputMoney();	break;
				case "2" : outputMoney();	break;
			}
			System.out.println();
		}
		while ( !con.equals("3") );

	}

	// 입금
	static void inputMoney() throws IOException
	{
		System.out.println();
		System.out.println("<거스름돈 입금>");
		System.out.println("입금할 금액을 입력해주세요.");
		System.out.println();

		// 입력 받은 입금 금액을 저장한 배열
		int[] m = writeMoney();


		// 입금 확인
		System.out.println("입금하시겠습니까? (Y/N)");
		do
		{
			System.out.print(">> ");
			con = br.readLine().toUpperCase();

			if (!(con.equals("Y") || con.equals("N")))
				System.out.println("잘못 입력하셨습니다.");
		}
		while (!(con.equals("Y") || con.equals("N")));


		if (con.equals("Y"))
		{
			for (int i=0; i<Library.money.length; i++)
				Library.money[i] += m[i];

			System.out.println("입금이 완료되었습니다.");
		}
		else
			System.out.println("입금이 취소되었습니다.");

		System.out.println();
	}
	
	// 출금
	static void outputMoney() throws IOException
	{
		System.out.println();
		System.out.println("<거스름돈 출금>");
		System.out.println("출금할 금액을 입력해주세요.");
		System.out.println();

		// 입력 받은 출금 금액을 저장한 배열
		int[] m = writeMoney();


		// 출금 확인
		System.out.println("출금하시겠습니까? (Y/N)");
		do
		{
			System.out.print(">> ");
			con = br.readLine().toUpperCase();

			if (!(con.equals("Y") || con.equals("N")))
				System.out.println("잘못 입력하셨습니다.");
		}
		while (!(con.equals("Y") || con.equals("N")));



		if ((con.equals("Y")))
		{
			boolean flag = true;	//-- 거스름돈이 출금할 만큼 있는지 확인하는 변수
									//	 (true: 있음, false: 없음)
	
			// 모든 돈이 있다는 걸 확인한 후, 일괄적으로 출금해야 한다.
			// ○ 잔돈 확인
			for (int i=0; i<Library.money.length; i++)
			{
				if (Library.money[i] < m[i])
				{
					flag = false;
					System.out.println("[출금 실패]");
					System.out.println("거스름돈이 모자랍니다.");
					break;
				}				
			}

			// ○ 잔돈 출금
			if (flag)
			{
				for (int i=0; i<Library.money.length; i++)
					Library.money[i] -= m[i];
				System.out.println("출금이 완료되었습니다.");
			}
		}
		else
			System.out.println("출금이 취소되었습니다.");

		System.out.println();
	}

	// 돈의 갯수를 입력 받아서 반환하는 배열
	static int[] writeMoney() throws IOException
	{
		int[] m = new int[5];	//-- 입출금할 돈의 갯수를 입력받을 배열
								//  : 순서대로 1만원, 5천원, 1천원, 5백원, 1백원 의 갯수
		
		System.out.print("10,000원: ");
		m[0] = Integer.parseInt(br.readLine());
		System.out.print("5,000원: ");
		m[1] = Integer.parseInt(br.readLine());
		System.out.print("1,000원: ");
		m[2] = Integer.parseInt(br.readLine());
		System.out.print("500원: ");
		m[3] = Integer.parseInt(br.readLine());
		System.out.print("100원: ");
		m[4] = Integer.parseInt(br.readLine());
		System.out.println();

		System.out.printf("총액: %,d원\n", moneyTot(m));
		
		return m;
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	// 5. 이용자모드
	static void userOn() throws IOException
	{
		System.out.println("\n===이용자 모드 시작===");
		UserSystem.onSystem();	
	}
	

	// 6. 프로그램 종료
	static void exit()
	{
		System.out.println("프로그램을 종료합니다.");
		System.exit(-1);			
	}
}
