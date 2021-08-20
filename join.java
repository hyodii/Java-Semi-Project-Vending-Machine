// 회원가입
	public static void join() throws IOException
	{
		// 인스턴스 선언
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		do
		{			
			System.out.print("회원가입하시겠습니까? (Y/N) :");
			con = br.readLine().toUpperCase();
		}
		while (!(con.equals("Y") || con.equals("N")));
		
		if (con.equals("Y"))
			makeNewMem();
		else
			memCheck();


		// 회원가입 완료 후 로그인 창으로 입력
		login();
	}


	// 새로운 계정을 생성하는 메소드
	private static void makeNewMem() throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String newId, newPw, newName, newSsn;		//-- 사용자로부터 입력 받을 정보들

		// 아이디 생성
		do
		{			
			System.out.print("■ 아이디 입력 : ");
			newId = br.readLine();

			if (members.containsKey(newId))
				System.out.println("중복된 아이디입니다!");
		}
		while (!members.containsKey(newId));
		

		// 비밀번호 입력
		System.out.print("■ 비밀번호 입력 : ");
		newPw = br.readLine();


		// 정보 입력
		System.out.println("■ 회원 정보 입력");		
		System.out.print("이름 : ");
		newName = br.readLine();

		do
		{
			System.out.print("주민등록번호 : ");
			newSsn = br.readLine();
			if (checkSsn(newSsn))	// 유효성 검사 메소드
				System.out.println("잘못된 주민등록번호입니다!");
		}
		while (!checkSsn(newSsn));


		// 자료구조에 정보 입력
		members.put(newId, new members(newPw, newName, newSsn));
		
		System.out.print("회원가입 완료!");
	}


	// 주민등록번호 유효성 검사 메소드
	static private boolean checkSsn(String str)
	{
		// 주요 변수 선언
		int[] chk = {2, 3, 4, 5, 6, 7, 0, 8, 9, 2, 3, 4, 5};	// 곱해지는 수		
		int tot = 0;			// 곱셈 연산 후 누적합을 담을 변수
		int su;					// 최종 연산 결과가 담을 변수

		
		// ① 입력된 주민등록번호 자릿수 확인
		if (str.length() != 14)
		{
			System.out.println(">> 입력 오류~!!!");
			return false;
		}


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
