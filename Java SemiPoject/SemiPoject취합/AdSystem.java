import java.util.Hashtable;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Calendar;
import java.util.GregorianCalendar;

//  AdSystemŬ���� �޼ҵ� ���� �Ϸ�~!

class AdSystem extends LibCommon
{
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	private static final String ADMIN_ID = "admin";
	private static final String ADMIN_PW = "1234";
	
	static String con;		//-- ���� �Է� ��Ʈ�� ��


	// ������ �޴� ����
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


	// ������ �޴� ���÷���
	static void menuDisp()
	{
		System.out.println();
		System.out.println("<< ������ ���� ���� >>");
		System.out.println("1. ���� ���� ��Ȳ");
		System.out.println("2. ���� ����");
		System.out.println("3. ȸ������ ��ȸ �� ����");
		System.out.println("4. ��ü�� ����");
		System.out.println("5. �̿��ڸ��");
		System.out.println("6. ���α׷� ����");
	}


	// ������ �޴� ����
	static public void menuSelect() throws IOException
	{
		do
		{	
			System.out.print(">> ");
			con = br.readLine();

			if (!(Integer.parseInt(con) >= 1 && Integer.parseInt(con) <= 6))
				System.out.println("�߸� �Է��ϼ̽��ϴ�.");
		}
		while (!(Integer.parseInt(con) >= 1 && Integer.parseInt(con) <= 6));
	}


	// ������ �޴� ����
	static public void menuRun() throws IOException
	{
		System.out.println(); 

		switch(con)
		{
			case "1" : selectInfoMenu();		break;	// ���� ���� ��Ȳ
			case "2" : selectAdminMenu();		break;	// ���� ����
			case "3" : selectMemCon();			break;	// ȸ������ ��ȸ �� ����
			case "4" : selectLateFee();			break;	// ��ü�� ����
			case "5" : userOn();				break;	// �̿��ڸ��
			case "6" : exit();					break;	// ���α׷� ����
		}
	}

	// ������ �α���
	static boolean login() throws IOException
	{
		String temp;	//-- �Է¹��� ��
	
		System.out.println();
		System.out.println("<< ������ �α��� >>");

		// ���̵� Ȯ��
		do
		{
			System.out.print("�� ���̵� : ");
			temp = br.readLine();

			if (!ADMIN_ID.equals(temp))
				System.out.println("�߸� �Է��ϼ̽��ϴ�.");
		}
		while (!ADMIN_ID.equals(temp));
		

		// �н����� Ȯ��
		do
		{
			System.out.print("�� ��й�ȣ : ");
			temp = br.readLine();

			if (!ADMIN_PW.equals(temp))
				System.out.println("�߸� �Է��ϼ̽��ϴ�.");
		}
		while (!ADMIN_PW.equals(temp));


		// �α��� ����
		System.out.println("�����ڷ� �α����Ͽ����ϴ�.");
		return true;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	// 1. ���� ���� ��Ȳ
	static void selectInfoMenu() throws IOException
	{
		do
		{
			System.out.println("<<���� ���� ��Ȳ>>");
			System.out.println("1. ���� ��Ȳ �˻�");
			System.out.println("2. ��ü ���� ��Ȳ ��ȸ");
			System.out.println("3. ��ü ���� ��Ȳ ��ȸ");
			System.out.println("0. ������ ���� �������� �̵�");

			do
			{	
				System.out.print(">> ");
				con = br.readLine();

				if ( Integer.parseInt(con) < 0 && Integer.parseInt(con) >3  )
					System.out.println("�߸� �Է��ϼ̽��ϴ�.");
			}
			while ( Integer.parseInt(con) < 0 && Integer.parseInt(con) >3);
			
			/*
			boolean on = true;
			switch(con)
			{
				case "1" : selectSearchMenu();	break;	// ���� ��Ȳ �˻�
				case "2" : overdueStatus();		break;	// ��ü ���� ��Ȳ ��ȸ
				case "3" : rentalStatus();		break;	// ��ü ���� ��Ȳ ��ȸ
				case "4" : on = false;			break;	// ������ �޴��� �̵� Back!
			}
			return on;
			*/

		
			switch(con)
			{
				case "1" : selectSearchMenu();	break;	// ���� ��Ȳ �˻�
				case "2" : overdueStatus();		break;	// ��ü ���� ��Ȳ ��ȸ
				case "3" : rentalStatus();		break;	// ��ü ���� ��Ȳ ��ȸ	
			}
		}
		while ( !con.equals("0") );
	}


	// 1-1. ���� ��Ȳ �˻�
	static void selectSearchMenu() throws IOException
	{

		System.out.println("\n<<���� ��Ȳ �˻�>>");
		System.out.println("1. ���� ����");
		System.out.println("2. �̿��� ID");
		System.out.println("3. �����޴���");

		do
		{	
			System.out.print(">> ");
			con = br.readLine();

			if (Integer.parseInt(con) < 0 && Integer.parseInt(con) >2)
				System.out.println("�߸� �Է��ϼ̽��ϴ�.");
		}
		while (Integer.parseInt(con) < 0 && Integer.parseInt(con) >2);
		
		switch(con)
		{
			case "1" : searchBook();	break;	// ���� ���� �˻�
			case "2" : searchId();		break;	// �̿��� ID �˻�
		}
		System.out.println();
	}


	// 1-1-1. ���� �������� �˻�
	static void searchBook() throws IOException
	{
		System.out.println("\n<<���� ���� �˻�>>");
		System.out.print("���� ���� : ");
		String title = br.readLine();
		
		System.out.println("\n<<���� ���� ��Ȳ>>");
		
		if(Library.bookList.containsKey(title)){				//���翩��
			Enumeration e = Library.bookList.keys();			//e�� bookList Ű��

			if(Library.bookList.get(title).isRental()){			//���Ⱑ�ɿ���
				System.out.printf("%s ��/�� ���� �����մϴ�.\n",title);
				selectSearchMenu();
			}else{
				for(int i=0; i<Library.rentList.size(); i++){
					RentalInfo valR = Library.rentList.get(i);

					if(valR.getRBook().equals(title)){			//rentlist�� �� å�� ������ ��
						System.out.printf("%s ��/�� ���� �������Դϴ�.", title);
						System.out.printf("\n���̿��� : %s", valR.getRMem());
							
						// �� �ݳ� ������ ���                  
						Calendar cal = new GregorianCalendar();			//-- �ݳ� �������� ���� ���� ����

						int y = valR.getRentalDate().get(Calendar.YEAR);
						int m = valR.getRentalDate().get(Calendar.MONTH);
						int d = valR.getRentalDate().get(Calendar.DATE);
						cal.set(y,m,d);									// ������ ��¥ ����   
						cal.add(Calendar.DATE, Library.RENT_DAYS);		// ������ ��¥ + ������� = �ݳ� ������
																		// Library.RENT_DAYS: final ������ ������ �������

						// ----------- �ݳ� ������ ��� �Ϸ�!
						  

						// �� ���� �ݳ� ���� ���
						Calendar today = new GregorianCalendar();		//-- ����                  
						long sec = (cal.getTimeInMillis() - today.getTimeInMillis())/ 1000;   // '�ݳ�������(�̷�) - ����' �� ��

						// �԰�¥�� ���糯¥�� ���̸� �� ������ ��ȯ
						// �� 1�� = 24(�ð�) * 60(��) * 60(��)
						//     ����, sec ������ 24*60*60 �� ���� ������ �� ���� �Ϸ� ��ȯ ����
						long days = sec / (24*60*60);
		   
						// �� ���
						if(days>=0)
							System.out.printf("\n������ �ݳ� ���� : %d��\n", days); 
						else
							System.out.printf("\n��%d�� °, ��ü ��\n", Math.abs(days));	//�������� ��ü ���� ��¥ ���
						
						return;
					}
				}
			}
		}else
			System.out.printf("\n%s ��/�� �������� �ʴ� �����Դϴ�.\n", title);  
		
	}


	// 1-1-2. �̿��� ID�� �˻�
	static void searchId() throws IOException
	{
		String tempId;   //-- ����ڷκ��� �Է� ���� ���� ���� ����

		System.out.println("\n<<�̿��� ID ��ȸ>>");
		System.out.print("ȸ�� ID: ");
		tempId = br.readLine();

		System.out.println();
		System.out.println("<< ȸ�� ID �˻� ��� >>");

		if (Library.memList.containsKey(tempId))   // �˻��� ID�� �ִٸ�
		{
			// valMem = �˻��� ȸ�� ����
			Members valMem = Library.memList.get(tempId);

			System.out.println("ȸ�� ID : " + tempId);
			System.out.println("�̸�    : " + valMem.getName());

			System.out.println();
			System.out.println("[���� ���� å ���]");
			System.out.printf("%15s \t%3s \t%4s\n", "������", "��ü", "��ü��");
			System.out.println("=======================================");

			int i = 1; //-- ����� å�� ������ ���� ����
			for (int idx=0; idx<Library.rentList.size(); idx++)   // ��
			{
				RentalInfo r = Library.rentList.get(idx);   //-- �̹��� �޾ƿ� ���� ����
						
				if (tempId.equals(r.getRMem()) && !r.isReturnBook() )   // ��, ��
					System.out.printf("[%d] %11s \t%3s \t%,4d\n", i++, r.getRBook(), overdue(r), calLateFee(r));
			}
			System.out.println("=======================================");

			System.out.printf("�� �̳� ��ü��: %,d��\n", calLateFee(tempId));
		}
		else
			System.out.println(tempId + "��/�� �������� �ʴ� ȸ���Դϴ�.");

		System.out.println();
	}

	
	// 1-2. ��ü ���� ��Ȳ ��ȸ
	static void overdueStatus() throws IOException
	{
		System.out.println("\n<<��ü ���� ��Ȳ ��ȸ>>");
		System.out.printf("%12s %7s %7s %5s %5s\n", "������", "�̿��ڸ�", "��������", "��ü�ϼ�", "��ü��");
		System.out.println("===================================================================");
		
		
		//���
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
	

	// 1-3. ��ü ���� ��Ȳ ��ȸ
	static void rentalStatus() throws IOException
	{
		System.out.println("\n<<��ü ���� ��Ȳ ��ȸ>>");
		System.out.printf("%12s %7s %7s %5s %5s\n", "������", "�̿��ڸ�", "��������", "��ü����", "��ü�ϼ�");
		System.out.println("===================================================================");
		
		
		//���
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


	// 2. ���� ���� ����
	static void selectAdminMenu() throws IOException
	{
		do
		{
			System.out.println("\n<<���� ���� ����>>");
			System.out.println("1. ���� �߰�");
			System.out.println("2. ��ü ���� ���");
			System.out.println("3. ��� ���� ����");
			System.out.println("4. �ű� �԰� ���� ���");
			System.out.println("0. ������ ���� �������� �̵�");

			do
			{	
				System.out.print(">> ");
				con = br.readLine();

				if ( Integer.parseInt(con) < 0 && Integer.parseInt(con) >4)
					System.out.println("�߸� �Է��ϼ̽��ϴ�.");
			}
			while ( Integer.parseInt(con) < 0 && Integer.parseInt(con) >4);
			
		
			switch(con)
			{
				case "1" : addBook();					break;	// �����߰�
				case "2" : allBookList();				break;	// ��ü ���� ���
				case "3" : wishBookCon();				break;	// ��� ���� ���
				case "4" : newBookListPrint();			break;	// �ű� �԰� ���� ���
			}
		}
		while ( !con.equals("0") );
	
	}

	// 2-1. ���� �߰�
	static void addBook() throws IOException
	{	
		String title, a, p, y, cN, ca;
		System.out.print("������ : ");
		title = br.readLine();
		System.out.print("���� : ");
		a = br.readLine();
		System.out.print("���ǻ� : ");
		p = br.readLine();
		System.out.print("���ǳ⵵ : ");
		y = br.readLine();
		System.out.print("�з���ȣ : ");
		cN = br.readLine();
		System.out.print("ī�װ� : ");
		ca = br.readLine();
		Calendar cal = new GregorianCalendar();

		Library.bookList.put(title, new Books(a,p,y,cN,ca, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)) );
		System.out.println("���� �߰� �Ϸ�!");
	}


	// 2-2. ��ü ���� ���
	static void allBookList() throws IOException
	{
		System.out.println("\n\t\t\t\t[��ü ���� ���]");
		System.out.println("===================================================================================================================");
		System.out.printf("%s %10s %10s %10s %10s %10s %12s %10s\n", "������", "���ڸ�", "���ǻ�", "���ǳ⵵", "�з���ȣ", "ī�װ�", "�԰�����", "���⿩��");
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


	// 2-3. ��� ���� ����
	static void wishBookCon() throws IOException
	{	
		//��� ���� ���
		System.out.println("\n===================================================================");
		System.out.printf("%12s %10s %5s %10s %5s\n", "������", "���ڸ�", "��û��", "��û����", "��û����");
		System.out.println("===================================================================");
		
		for(int i=0; i<Library.wishList.size(); i++){
			Wish valW = Library.wishList.get(i);
			System.out.printf("%12s %10s %5s %10s %5s\n",valW.getWTitle(),valW.getWAuthor()
							,valW.getWMem(), valW.getReqDateStr(), valW.getRequestStr());
	}

		//��û�źθ޴�
		System.out.println("1. ��û�ź�");
		System.out.println("2. �����޴���");

		do
		{	
			System.out.print(">> ");
			con = br.readLine();

			if ( Integer.parseInt(con) < 1 && Integer.parseInt(con) >2 )
				System.out.println("�߸� �Է��ϼ̽��ϴ�.");
		}
		while ( Integer.parseInt(con) < 1 && Integer.parseInt(con) >2);
		
		switch(con)
		{
			case "1" : wishReject();			break;	// ��û�ź�
			case "2" : return;							// �����޴���
		}
	}
	
	static void wishReject() throws IOException
	{
		System.out.println("\n[��� ���� ��û �ź�]");
		System.out.print("������ �Է� : ");
		String title = br.readLine();

		for(int i=0; i<Library.wishList.size(); i++){
			Wish valW = Library.wishList.get(i);

			if((valW.getWTitle()).equals(title)){
				valW.setRequest(3);
				System.out.printf("\n%s ��/�� ��û �źεǾ����ϴ�.\n",title);
				selectAdminMenu();
				return;
			}
			else{
				if(i==Library.wishList.size()-1)//{
					System.out.printf("%s ��/�� ��� ������ �������� �ʽ��ϴ�.\n",title);
								
				continue;	//wishList ���������� �����ϴ��� Ȯ���ϱ� ���� i��° �ش� �ݺ����� Ż��
			}
		}
	}


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	// 3. ȸ������ ��ȸ �� ����
	static void selectMemCon() throws IOException
	{
		do
		{
			// ����â ���÷���
			System.out.println("<ȸ������ ��ȸ �� ����>");
			System.out.println("1. ��ü ȸ�� ��ȸ"); 
			System.out.println("2. ȸ�� �˻�"); 
			System.out.println("3. ȸ�� ����"); 
			System.out.println("0. ������ ���� �������� �̵�");
		
			// ���� �ޱ�
			do
			{	
				System.out.print(">> ");
				con = br.readLine();

				if (!(Integer.parseInt(con) >= 0 && Integer.parseInt(con) <= 3))
					System.out.println("�߸� �Է��ϼ̽��ϴ�.");
			}
			while (!(Integer.parseInt(con) >= 0 && Integer.parseInt(con) <= 3));

			// ����
			System.out.println(); 

			
			switch(con)
			{
				case "1" : printAllMem();	break;	// ��ü ȸ�� ��ȸ
				case "2" : searchMem();		break;	// ȸ�� �˻�
				case "3" : removeMem();		break;	// ȸ�� ����
			}
		}
		while ( !con.equals("0") );

		
	}


	// 3-1. ��ü ȸ�� ��ȸ
	private static void printAllMem()
	{
		System.out.println("\t\t[��ü ȸ�� ��ȸ]");
		System.out.println("===================================================================");
		System.out.printf("%7s \t%5s \t%8s \t%3s \t%6s \n", "ID", "�̸�", "���⵵��(��)", "��ü", "�̳���ü��");
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
	
	// 3-2. ȸ�� �˻�
	private static void searchMem() throws IOException
	{
		String tempId;	//-- ����ڷκ��� �Է� ���� ���� ���� ����

		System.out.println("<ȸ������ ��ȸ>");
		System.out.print("ȸ�� ID: ");
		tempId = br.readLine();

		System.out.println();
		System.out.println("<< ȸ�� ID �˻� ��� >>");

		if (Library.memList.containsKey(tempId))	// �˻��� ID�� �ִٸ�
		{
			// valMem = �˻��� ȸ�� ����
			Members valMem = Library.memList.get(tempId);

			System.out.println("ȸ�� ID : " + tempId);
			System.out.println("�̸�    : " + valMem.getName());

			// ���� ���� å ���
			//	�� ��������(Library.rentList)�� ��ü������ Ȯ���Ͽ�,
			//	�� �Է� ���� ID(tempId) �� �������� id(rentInfo �� rMem)�� �����ϰ�, 
			//		���� ��(rentInfo �� returnBook)�� ���� ã�Ƽ� (�� returnBook �� true: �ݳ��Ϸ�, false: ���� ��)
			//	�� �� ���� ������ ���!
			
			System.out.println();
			System.out.println("[���� ���� å ���]");
			System.out.printf("%10s \t%3s \t%4s\n", "������", "��ü", "��ü��");
			System.out.println("============================================");

			int i = 1; //-- ����� å�� ������ ���� ����
			for (int idx=0; idx<Library.rentList.size(); idx++)	// ��
			{
				RentalInfo r = Library.rentList.get(idx);	//-- �̹��� �޾ƿ� ���� ����
								
				if (tempId.equals(r.getRMem()) && !r.isReturnBook() )	// ��, ��
					System.out.printf("[%d] %9s \t%3s \t%,4d\n", i++, r.getRBook(), overdue(r), calLateFee(r));
			}
			System.out.println("============================================");

			System.out.printf("�� �̳� ��ü��: %,d��\n", calLateFee(tempId));
		}
		else
			System.out.println(tempId + "��/�� �������� �ʴ� ȸ���Դϴ�.");

		System.out.println();
	}

	// 3-3. ȸ�� ����
	private static void removeMem() throws IOException		
	{
		String temp;

		System.out.println("<ȸ������ ����>");
		System.out.print("ȸ�� ID: ");
		temp = br.readLine();

		System.out.println();
		System.out.println("<< ȸ�� ID ���� ��� >>");

		if (Library.memList.containsKey(temp))	// �˻��� ID�� �ִٸ�
		{
			Library.memList.remove(temp);

			System.out.println("ȸ�� " + temp + "��/�� �����߽��ϴ�.");
		}
		else
			System.out.println(temp + "��/�� �������� �ʴ� ȸ���Դϴ�.");
		System.out.println();
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	// 4. ��ü�� ����
	static void selectLateFee() throws IOException
	{
		do
		{
			// ����â ���÷���
			System.out.println("<��ü�� ����>");
			System.out.println("1. �� ��ü�� ��ȸ"); 
			System.out.println("2. ȸ���� ��ü�� �̷� ��ȸ"); 
			System.out.println("3. ������ ��ü�� �̷� ��ȸ"); 
			System.out.println("4. �Ž����� ��ȸ �� �����"); 
			System.out.println("0. ������ ���� �������� �̵�");
		
			// ��ɾ� �Է¹ޱ�
			do
			{	
				System.out.print(">> ");
				con = br.readLine();

				if (!(Integer.parseInt(con) >= 0 && Integer.parseInt(con) <= 4) )
					System.out.println("�߸� �Է��ϼ̽��ϴ�.");
			}
			while (!(Integer.parseInt(con) >= 0 && Integer.parseInt(con) <= 4) );

			// ����
			System.out.println(); 

			switch(con)
			{
				case "1" : printAllLateFee();		break;	// �� ��ü�� ��ȸ
				case "2" : printMemLateFee();		break;	// ȸ���� ��ü�� �̷� ��ȸ
				case "3" : printBookLateFee();		break;	// ������ ��ü�� �̷� ��ȸ
				case "4" : manageMoney();			break;	// �Ž����� ��ȸ �� �����
			}
		}
		while ( !con.equals("0") );
		
	}

	// 4-1. �� ��ü�� ��ȸ
	static void printAllLateFee()
	{
		// �� rentList �� ���� üũ�ϸ鼭
		// �� �ݳ� O �ڷ� : �� ��ü�ῡ ������
		// �� �ݳ� X �ڷ� : �̳���ü�ῡ ������
		// �� �ݳ�����: rentInfo �� returnBook �� true: �ݳ��Ϸ�, false: ���� ��

		int tot1 = 0; //-- �� ��ü�� 
		int tot2 = 0; //-- �̳���ü�� 

		for (int idx=0; idx<Library.rentList.size(); idx++)	// ��
		{
			RentalInfo r = Library.rentList.get(idx);	//-- �̹��� �޾ƿ� ���� ����
							
			if (r.isReturnBook())	// ��
				tot1 += r.getLateFee();
			else
				tot2 += calLateFee(r);
		}

		// ���
		System.out.println("<�� ��ü�� ��ȸ>");
		System.out.printf("�� ��ü��   : %,d��\n", tot1);
		System.out.printf("�̳� ��ü�� : %,d��\n", tot2);
		System.out.println();
	}

	// 4-2. ȸ���� ��ü�� �̷� ��ȸ
	static void printMemLateFee()
	{
		// �� memList �� ��ü üũ�ϸ鼭
		// �� calLateFee() �޼ҵ��, ����/�̳� ���

		System.out.println("<ȸ���� ��ü�� ��ȸ>");
		System.out.printf("%10s \t%5s \t%6s \t%6s\n", "ȸ��ID", "�̸�", "���� ��ü��", "�̳� ��ü��");
		System.out.println("==================================================================");


		Enumeration e = Library.memList.keys();	// Ű�� ������
  
		while(e.hasMoreElements())	// ��
		{
			String id = (String)e.nextElement();
			Members m = Library.memList.get(id);

			System.out.printf("%10s \t%5s \t  %,6d \t  %,6d\n", 
								id, m.getName(), calLateFee(id, true), calLateFee(id, false));
		}		
		System.out.println();
	}

	// 4-3. ������ ��ü�� �̷� ��ȸ
	static void printBookLateFee()
	{
		// �� bookList �� ��ü üũ�ϸ鼭
		// �� calLateFee() �޼ҵ��, ����/�̳� ���

		System.out.println("<������ ��ü�� ��ȸ>");
		System.out.printf("%10s \t%5s \t%6s \t%6s\n", "������", "����", "���� ��ü��", "�̳� ��ü��");
		System.out.println("==================================================================");


		Enumeration e = Library.bookList.keys();	// Ű�� ������
  
		while(e.hasMoreElements())	// ��
		{
			String key = (String)e.nextElement();
			Books m = Library.bookList.get(key);

			System.out.printf("%10s \t%5s \t  %,6d \t  %,6d\n", 
								key, m.getAuthor(), calLateFee(key, true, false), calLateFee(key, false, false));
		}		
		System.out.println();
	}

	
	// 4-4. �Ž����� ��ȸ �� �����
	static void manageMoney() throws IOException
	{
		do
		{
			// �۰Ž����� ��ȸ
			System.out.println("<�Ž����� ��ȸ �� �����>");
			System.out.printf("���� �Ž������� %,d�� �ֽ��ϴ�.\n\n", moneyTot(Library.money));
			
			// money �迭: ������� 1����, 5õ��, 1õ��, 5���, 1��� �� ����
			System.out.printf("10,000��: %d��\n", Library.money[0]);
			System.out.printf("5,000��: %d��\n", Library.money[1]);
			System.out.printf("1,000��: %d��\n", Library.money[2]);
			System.out.printf("500��: %d��\n", Library.money[3]);
			System.out.printf("100��: %d��\n", Library.money[4]);
			System.out.println("");

			
			// �� �޴� ����â
			System.out.println("1. �Ž����� �Ա�");
			System.out.println("2. �Ž����� ���");
			System.out.println("3. �޴���");


			// �� ���� �Է� �ޱ�
			do
			{	
				System.out.print(">> ");
				con = br.readLine();

				if (!(Integer.parseInt(con) >= 1 && Integer.parseInt(con) <= 3))
					System.out.println("�߸� �Է��ϼ̽��ϴ�.");
			}
			while (!(Integer.parseInt(con) >= 1 && Integer.parseInt(con) <= 3));
			
			
			// �� ����
			switch (con)
			{
				case "1" : inputMoney();	break;
				case "2" : outputMoney();	break;
			}
			System.out.println();
		}
		while ( !con.equals("3") );

	}

	// �Ա�
	static void inputMoney() throws IOException
	{
		System.out.println();
		System.out.println("<�Ž����� �Ա�>");
		System.out.println("�Ա��� �ݾ��� �Է����ּ���.");
		System.out.println();

		// �Է� ���� �Ա� �ݾ��� ������ �迭
		int[] m = writeMoney();


		// �Ա� Ȯ��
		System.out.println("�Ա��Ͻðڽ��ϱ�? (Y/N)");
		do
		{
			System.out.print(">> ");
			con = br.readLine().toUpperCase();

			if (!(con.equals("Y") || con.equals("N")))
				System.out.println("�߸� �Է��ϼ̽��ϴ�.");
		}
		while (!(con.equals("Y") || con.equals("N")));


		if (con.equals("Y"))
		{
			for (int i=0; i<Library.money.length; i++)
				Library.money[i] += m[i];

			System.out.println("�Ա��� �Ϸ�Ǿ����ϴ�.");
		}
		else
			System.out.println("�Ա��� ��ҵǾ����ϴ�.");

		System.out.println();
	}
	
	// ���
	static void outputMoney() throws IOException
	{
		System.out.println();
		System.out.println("<�Ž����� ���>");
		System.out.println("����� �ݾ��� �Է����ּ���.");
		System.out.println();

		// �Է� ���� ��� �ݾ��� ������ �迭
		int[] m = writeMoney();


		// ��� Ȯ��
		System.out.println("����Ͻðڽ��ϱ�? (Y/N)");
		do
		{
			System.out.print(">> ");
			con = br.readLine().toUpperCase();

			if (!(con.equals("Y") || con.equals("N")))
				System.out.println("�߸� �Է��ϼ̽��ϴ�.");
		}
		while (!(con.equals("Y") || con.equals("N")));



		if ((con.equals("Y")))
		{
			boolean flag = true;	//-- �Ž������� ����� ��ŭ �ִ��� Ȯ���ϴ� ����
									//	 (true: ����, false: ����)
	
			// ��� ���� �ִٴ� �� Ȯ���� ��, �ϰ������� ����ؾ� �Ѵ�.
			// �� �ܵ� Ȯ��
			for (int i=0; i<Library.money.length; i++)
			{
				if (Library.money[i] < m[i])
				{
					flag = false;
					System.out.println("[��� ����]");
					System.out.println("�Ž������� ���ڶ��ϴ�.");
					break;
				}				
			}

			// �� �ܵ� ���
			if (flag)
			{
				for (int i=0; i<Library.money.length; i++)
					Library.money[i] -= m[i];
				System.out.println("����� �Ϸ�Ǿ����ϴ�.");
			}
		}
		else
			System.out.println("����� ��ҵǾ����ϴ�.");

		System.out.println();
	}

	// ���� ������ �Է� �޾Ƽ� ��ȯ�ϴ� �迭
	static int[] writeMoney() throws IOException
	{
		int[] m = new int[5];	//-- ������� ���� ������ �Է¹��� �迭
								//  : ������� 1����, 5õ��, 1õ��, 5���, 1��� �� ����
		
		System.out.print("10,000��: ");
		m[0] = Integer.parseInt(br.readLine());
		System.out.print("5,000��: ");
		m[1] = Integer.parseInt(br.readLine());
		System.out.print("1,000��: ");
		m[2] = Integer.parseInt(br.readLine());
		System.out.print("500��: ");
		m[3] = Integer.parseInt(br.readLine());
		System.out.print("100��: ");
		m[4] = Integer.parseInt(br.readLine());
		System.out.println();

		System.out.printf("�Ѿ�: %,d��\n", moneyTot(m));
		
		return m;
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	// 5. �̿��ڸ��
	static void userOn() throws IOException
	{
		System.out.println("\n===�̿��� ��� ����===");
		UserSystem.onSystem();	
	}
	

	// 6. ���α׷� ����
	static void exit()
	{
		System.out.println("���α׷��� �����մϴ�.");
		System.exit(-1);			
	}
}