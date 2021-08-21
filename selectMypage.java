// 마이페이지
	static void selectMypage() throws IOException
	{
		System.out.println();
		System.out.println("<< 마이페이지 >>");
		System.out.println("1. 내 정보");
		System.out.println("2. 나의 대출 현황");
		System.out.println("3. 나의 희망 도서 신청 현황");
	
		 do
		 {
			System.out.println();
			System.out.print("메뉴를 선택해주세요 >> ");
		
			con = br.readLine();

			if (!(Integer.parseInt(con) >= 1 && Integer.parseInt(con) <= 3))
			   System.out.println("잘못 입력하셨습니다.");
		 }
		 while (!(Integer.parseInt(con) >= 1 && Integer.parseInt(con) <= 3)); 

		switch(con)
		{
			 case "1" : myInfo();				break;   // 내 정보
			 case "2" : myRentalStatus();		break;   // 나의 대출 현황
			 case "3" : myWishBookStatus();		break;   // 나의 희망 도서 신청 현황
		}

	}


	 
	
	static void myInfo() throws IOException
	{		
		System.out.println();
		System.out.println("<< 마이페이지 >>");
		
		Enumeration e = Library.memList.keys();
		
		//temp와 user이름과 일치하는지 확인하고 아이디, 이름, 주민번호 밸류값을 뽑는다.
		while (e.hasMoreElements())
		{
			if (nowId.equals(e.nextElement()))	//현재아이디랑 멤버아이디의 같은 키값일 떄
			{
				System.out.print("▶ 아이디 : " + nowId);    
				System.out.print("\n▶ 이름 :" + Library.memList.get(nowId).getName());
				System.out.print("\n▶ 주민번호 : " + Library.memList.get(nowId).getSsn());
				System.out.println();
			}
		}
		
		menuDisp();
	}

	


	static void myRentalStatus() throws IOException
	{
		
		
		/*
		//String temp;

		System.out.println("<< 나의 대출 현황 >>");
		System.out.println();

		System.out.print("▶ 대출 권수 : "); //+ count총권수 );
		System.out.println("\n▶ 대출한 도서\n");
		System.out.println("도서명      대출 일자       남은 기한         연체여부      연체일수      연체료");
		System.out.println("======================================================================================");
		

		//Enumeration lala = Library.memList.keys();

		Enumeration e = Library.memList.keys();
		while (e.hasMoreElements())
		{
			if ( title.equals( (String)e.nextElement()) )
			{
				System.out.println( title +"   "
									+ (Library.bookList.get(title)).get대출일자() +"   "
									+ (Library.bookList.get(title)).get남은기한() +"   "
									+ (Library.bookList.get(title)).get연체여부() +"   "
									+ (Library.bookList.get(title)).get연체일수() +"   "
									+ (Library.bookList.get(title)).get연체료() );            
			}
		}

		selectMypage();
		*/
		
		
		

	}
	
