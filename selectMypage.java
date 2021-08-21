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


	 
	
	// 내 정보
	static void myInfo() throws IOException  //--완료
	{		
		System.out.println();
		System.out.println("<< 마이페이지 >>");
		
		Enumeration e = Library.memList.keys();
		
		//현재아이디와 user이름과 일치하는지 확인하고 아이디, 이름, 주민번호 밸류값을 뽑는다.
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
		
		
	}

	

	// 나의 대출 현황
	static void myRentalStatus() throws IOException
	{
		
		System.out.println("<< 나의 대출 현황 >>");
		System.out.println();

		System.out.print("▶ 대출 권수 : " + c);//+ bookList.size()); //+ count총권수 );
				
		System.out.println("\n▶ 대출한 도서\n");
		System.out.println("도서명      대출 일자       남은 기한         연체여부      연체일수      연체료");
		System.out.println("======================================================================================");
		
		String [] ids = new String [Library.rentList.size()];
		int i =0;
				

		
		ListIterator<RentalInfo> r = Library.rentList.listIterator();
		while (r.hasNext())
		{
			RentalInfo ren2 = r.next();
			ids[i++] = ren2.getRMem();
		}

		if (Arrays.asList(ids).contains(nowId))
		{		
			ListIterator<RentalInfo> r1 = Library.rentList.listIterator();
			while (r1.hasNext())
			{
				RentalInfo ren = r1.next();
				if(nowId.equals(ren.getRMem()))
				{	
					System.out.printf("%8s %10s %5s %s %s %s\n",ren.getRBook(), ren.getRentalDateStr()
															, "남은기한", "연체여부"
															, "연체일수", "연체료");
					
				}

			}
			
		}
		

		else
		{
			System.out.println("대출한 책이 없습니다.");
		}


		selectMypage();
	
	}
	



	// 나의 희망 도서 신청 현황
	static void myWishBookStatus()throws IOException
	{
		Members mem = Library.memList.get(nowId);  // Menbers 타입 mem	
		String name = mem.getName();	// String name 에 현재 아이디의 회원이름(value값안에)을 담는다
		String [] names = new String [Library.wishList.size()];
		int i = 0;

		
		System.out.println("<< 나의 희망 도서 신청 현황 >>");
		System.out.println();
		
		ListIterator<Wish> li2 = Library.wishList.listIterator();
		while (li2.hasNext())
		{
			Wish wish2 = li2.next();
			names [i++] = wish2.getWMem();  // 신청이름들이 들어옴
		}

		if (Arrays.asList(names).contains(name))  // 문자열이 포함되어있는지 여부  contains와 같은 것 
		{
			System.out.println("도서명      저자명      신청 일자         신청 상태");
			System.out.println("=====================================================");
			
			ListIterator<Wish> li = Library.wishList.listIterator();  //Wish 타입의 객체를 받아오는 거니까
			while (li.hasNext())
			{
				Wish wish = li.next(); 
				if (wish.getWMem().equals(name))  //위시에서 신청자명만 가져와서 신청자명이랑 현재로그인된회원의 이름이 같을때
				{
					System.out.printf("%7s %5s %12s %12s\n"
								, wish.getWTitle(), wish.getWAuthor()												
								,wish.getReqDateStr(), wish.getRequestStr());  //<< 신청날짜랑 상태 오류
				}
				  
			}
			
			//테스트
			//System.out.println("신청하신 도서가 있습니다.");


		}
		
		else
			System.out.println("신청하신 희망도서가 없습니다.");
			
	
	
		System.out.println();
	}
