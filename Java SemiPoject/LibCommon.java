import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Enumeration;

/*
interface LibInterface
{
	public void login();
	public void menuDisp();
	public void menuSelect();
	public void menuRun(String s);
}
*/


class LibCommon //implements LibInterface	
{

	// 현재 있는 돈 총합
	public static int moneyTot(int[] m)
	{   
		return (10000*m[0]) + (5000*m[1]) + (1000*m[2]) + (500*m[3]) + (100*m[4]);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////


	// 연체 여부 확인① : 입력 받은 대출현황에 대한 연체 여부
	public static String overdue(RentalInfo r)            
	{         
		if (calLateDays(r) == 0)
			return "N";
		else
			return "Y";
	}


	// 연체 여부 확인② : 입력 받은 이용자에 대한 연체 여부  ※isOverdueStr 대체
	public static String overdue(String id) // throws IOException             
	{   
		String result = "-";
		for(int i=0; i<Library.rentList.size(); i++)
		{
			RentalInfo valR = Library.rentList.get(i);
			if((valR.getRMem()).equals(id))
			{
				if(!(valR.isReturnBook()) && calLateFee(valR)>0)   //반납X, 연체료가 있으면 현재 연체중
					result = "Y";
				else
					result = "N";
			}
		}
		return result;
	}


	// 연체료 계산① (매개변수 대출정보) : 입력 받은 대출현황에 대한 미납 연체료
	public static int calLateFee(RentalInfo r)         
	{
		return calLateDays(r) * Library.LATE_FEE;
	}


	// 연체료 계산② (매개변수 id) : 입력 받은 이용자의 총 미납 연체료
	public static int calLateFee(String id)
	{
		int result = 0; //-- 미납 연체료 누적합을 담을 변수 선언

		// ① 대출 현황 목록에서 입력 받은 이용자(id)의 대출 현황을 찾아내어,
		// ② 미납일 경우
		// ③ 누적합 구하기
		for(int i=0; i<Library.rentList.size(); i++)
		{
			RentalInfo valR = Library.rentList.get(i);

			if((valR.getRMem()).equals(id) && !valR.isReturnBook())   // ① && ②
			result += calLateFee(valR);   // ③
		}

		return result;
	}

	// 연체료 계산 ③ (매개변수 id, f) : 입력 받은 이용자의 총 연체료(납부/미납)
	public static int calLateFee(String id, boolean f)
	{
		// 반납여부 여부(true: 반납완료, false: 대출 중) 
		// f = true  일 경우 → 납부 연체료 반환
		//     false 일 경우 → 미납 연체료 반환

		int result1 = 0; //-- 납부 연체료 누적합을 담을 변수 선언
		int result2 = 0; //-- 미납 연체료 누적합을 담을 변수 선언

		// ① 대출 현황 목록에서 입력 받은 이용자(id)의 대출 현황을 찾아내어,
		// ② 반납 / 미납일 경우
		// ③ 누적합 구하기
		for(int i=0; i<Library.rentList.size(); i++)
		{
			RentalInfo valR = Library.rentList.get(i);

			if((valR.getRMem()).equals(id) && valR.isReturnBook())         // ① && ② : 반납 O
				result1 += valR.getLateFee();   // ③
			else if ((valR.getRMem()).equals(id) && !valR.isReturnBook())   // ① && ② : 반납 X
				result2 += calLateFee(valR);   // ③
		}

		if (f)
			return result1;
		else
			return result2;
	}

	// 연체료 계산 ④ : 입력 받은 이용자 혹은 도서의 총 연체료(납부/미납)
	// -->String, boolean 값을 받는 메소드가 기존에 있기 때문에(이용자 전용 메소드) 오버로딩이 안 됨.
	//    따라서 도서 여부를 판단하는 boolean 값을 추가로 설정
	//     (true: 이용자의 연체료, false: 도서의 연체료)
	public static int calLateFee(String k, boolean f, boolean b)
	{
		// 반납여부 여부(true: 반납완료, false: 대출 중) 
		// f = true  일 경우 → 납부 연체료 반환
		//     false 일 경우 → 미납 연체료 반환

		int result1 = 0; //-- 납부 연체료 누적합을 담을 변수 선언
		int result2 = 0; //-- 미납 연체료 누적합을 담을 변수 선언

		// ① 대출 현황 목록에서 입력 받은 이용자(id) 혹은 도서(title)의 대출 현황을 찾아내어,
		// ② 반납 / 미납일 경우
		// ③ 누적합 구하기

		if (b)
		{
			for(int i=0; i<Library.rentList.size(); i++)
			{
				RentalInfo valR = Library.rentList.get(i);

				if((valR.getRMem()).equals(k) && valR.isReturnBook())         // ① && ② : 반납 O
					result1 += valR.getLateFee();   // ③
				else if ((valR.getRMem()).equals(k) && !valR.isReturnBook())   // ① && ② : 반납 X
					result2 += calLateFee(valR);   // ③
			}
		}
		else
		{         
			for(int i=0; i<Library.rentList.size(); i++)
			{
				RentalInfo valR = Library.rentList.get(i);

				if((valR.getRBook()).equals(k) && valR.isReturnBook())         // ① && ② : 반납 O
					result1 += valR.getLateFee();   // ③
				else if ((valR.getRBook()).equals(k) && !valR.isReturnBook())   // ① && ② : 반납 X
					result2 += calLateFee(valR);   // ③
			}
		}
      
		if (f)
			return result1;
		else
			return result2;
	}


	// 연체일수 계산
	public static int calLateDays(RentalInfo r)         
	{
		int result=0; 
		//대출한 날짜+14일(Library.RENT_DAYS) ~ 로부터 오늘(이 메소드를 불러오는 시점)까지 몇일이 지났는지..

		// ○ 반납 예정일 계산                  
		Calendar cal = new GregorianCalendar();       //-- 반납 예정일을 담을 변수 선언

		int y = r.getRentalDate().get(Calendar.YEAR);
		int m = r.getRentalDate().get(Calendar.MONTH);
		int d = r.getRentalDate().get(Calendar.DATE);
		cal.set(y,m,d);                        // 대출한 날짜 셋팅   
		cal.add(Calendar.DATE, Library.RENT_DAYS);   // 대출한 날짜 + 대출기한 = 반납 예정일

		// ○ 반납 예정일 ~ 오늘까지 날 수 계산
		Calendar today = new GregorianCalendar();   //-- 오늘         

		long tempR = today.getTimeInMillis() - cal.getTimeInMillis();			//오늘 - 반납예정일
		if ( tempR<=0 )            //   반납예정일이 아직 오지 않음 (미래)      
			result = 0;
		else if (tempR>0)          //   반납예정일이 지남 (과거)   
		{
			long sec = (today.getTimeInMillis() - cal.getTimeInMillis())/ 1000;   // '오늘 - 반납예정일(과거)' 의 초

			// 입고날짜와 현재날짜의 차이를 일 단위로 변환
			// ※ 1일 = 24(시간) * 60(분) * 60(초)
			//     따라서, sec 값에서 24*60*60 한 값을 나누면 초 에서 일로 변환 가능
			long days = sec / (24*60*60);

			result = (int)days;
		}

		return result;
	}


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	// 신간도서목록 출력
	public static void newBookListPrint()             
	{
		Enumeration titles = Library.bookList.keys();   // 키값 얻어오기
		Calendar today = new GregorianCalendar();      // 현재 날짜 cal에 담기
                  
      
		System.out.println("\n[신규 입고 도서 목록]");
		System.out.println("===========================================================================================================");
		System.out.printf("도서명 \t저자명 \t출판사 \t출판년도 \t분류번호 \t카테고리 \t입고일자 \t대출여부\n");
		System.out.println("===========================================================================================================");
         
		while (titles.hasMoreElements()) // 키가 존재하는 한 루프
		{
			String title = String.valueOf(titles.nextElement());   // 다음번째 키로 넘어감
         
			// 입고날짜와 현재날짜의 차이를 초 단위로 변환 (천분의 1초 단위로 받아온 후 1000으로 나눔)
			long sec = (today.getTimeInMillis() - Library.bookList.get(title).getStoredDate().getTimeInMillis()) / 1000;
            
			// 입고날짜와 현재날짜의 차이를 일 단위로 변환
			// ※ 1일 = 24(시간) * 60(분) * 60(초)
			//     따라서, sec 값에서 24*60*60 한 값을 나누면 초 에서 일로 변환 가능
			long days = sec / (24*60*60);
       
			// 입고날짜와 현재날짜의 차이가 31일 미만인 값 출력
			if (days < 31L)    
			{
				System.out.println( title +"   "
									+ (Library.bookList.get(title)).getAuthor() +"   "
									+ (Library.bookList.get(title)).getPublisher() +"   "
									+ (Library.bookList.get(title)).getPubYear() +"   "
									+ (Library.bookList.get(title)).getCodeNumber() +"   "
									+ (Library.bookList.get(title)).getCategory() +"   "
									+ (Library.bookList.get(title)).getStoredDateStr() +"   "
									+ (Library.bookList.get(title)).isRental());  
			}
		}
	}



}