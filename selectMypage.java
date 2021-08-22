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
		int c = 0; 
		ListIterator<RentalInfo> r = Library.rentList.listIterator();	
		while (r.hasNext())		
		{
			RentalInfo ren = r.next();	
			if( (nowId.equals(ren.getRMem())) && (!ren.isReturnBook()) )	// 내이름 + 반납되지 않은 책 -> 권수
			{	
				c++;
			}
		}

		System.out.println("<< 나의 대출 현황 >>");
		System.out.println();
		System.out.print("▶ 대출 권수 : " + c);
		System.out.println("\n▶ 대출한 도서\n");
		System.out.println("도서명      대출 일자       남은 기한         연체여부      연체일수      연체료");
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
					long tempR = today.getTimeInMillis() - cal.getTimeInMillis();	//오늘 - 반납예정일
					if (tempR<=0)	//반납 예정일이 미래일 때 값이 마이너스 나옴
					{
						long sec = (cal.getTimeInMillis() - today.getTimeInMillis())/ 1000;   // '반납예정일(미래) - 오늘' 의 초

						// 입고날짜와 현재날짜의 차이를 일 단위로 변환
						// ※ 1일 = 24(시간) * 60(분) * 60(초)
						//     따라서, sec 값에서 24*60*60 한 값을 나누면 초 에서 일로 변환 가능
						long days = sec / (24*60*60);
						int result = (int)days;		//-- 남은 기한

						System.out.printf("%8s %10s %5d일 %8s %8d %7d\n",ren.getRBook(), ren.getRentalDateStr()
															,result, overdue(nowId)
															, calLateDays(ren), calLateFee(ren));
					}
					else if(tempR>0)	// 반납 예정일이 이미 지나갔을 때 - 연체발생
					{
						System.out.printf("%8s %10s 0일 %8s %8d %7d\n",ren.getRBook(), ren.getRentalDateStr()
															,overdue(nowId)
															, calLateDays(ren), calLateFee(ren));
					}
				}
			}
		}
		else
			System.out.println("대출한 이력이 없습니다.");
                     
	
	}
	


	// 나의 희망 도서 신청 현황
	static void myWishBookStatus()throws IOException
	{
		Members mem = Library.memList.get(nowId);  // Menbers 타입 mem에 현재 아이디를 담는다	
		String name = mem.getName();	// String name 에 현재 아이디의 회원이름(value값안에)을 담는다
		String [] names = new String [Library.wishList.size()];
		int i = 0;

		
		System.out.println("<< 나의 희망 도서 신청 현황 >>");
		System.out.println();
		
		ListIterator<Wish> li2 = Library.wishList.listIterator();
		while (li2.hasNext())
		{
			Wish wish2 = li2.next();	// wish에 있는 다음 요소를 반환
			names [i++] = wish2.getWMem();  // 신청이름들이 들어옴
		}

		if (Arrays.asList(names).contains(name))  // 문자열이 포함되어있는지 여부  contains와 같은 것 
		{
			System.out.println("도서명      저자명      신청 일자         신청 상태");
			System.out.println("=====================================================");
			
			ListIterator<Wish> li = Library.wishList.listIterator(); 
			while (li.hasNext())
			{
				Wish wish = li.next(); 
				if (wish.getWMem().equals(name))  //위시에서 신청자명만 가져와서 신청자명이랑 현재로그인된회원의 이름이 같을때
				{
					System.out.printf("%7s %5s %12s %12s\n"
								, wish.getWTitle(), wish.getWAuthor()												
								,wish.getReqDateStr(), wish.getRequestStr());  
				}
				  
			}
			
			//테스트
			//System.out.println("신청하신 도서가 있습니다.");


		}
		
		else
			System.out.println("신청하신 희망도서가 없습니다.");
			
	
	
		System.out.println();

	}
