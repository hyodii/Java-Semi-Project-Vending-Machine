import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Enumeration;
import java.util.ListIterator;
import java.util.Arrays;

// UserSystemŬ���� �޼ҵ� ���� �Ϸ�~!

class UserSystem extends LibCommon
{
	// Ŭ���� �ν��Ͻ�, ���� ����
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static String nowId;	//-- ���� �α��� �Ǿ� �ִ� ����
	static String con;		//-- ���� �Է� ��Ʈ�� ��
	static boolean on;		//-- �̿��ڸ�� ��� ���� Ȯ��(true: ���, false: �̿��ڸ�� ��� ����)
	static boolean login;	//-- �α��� ���� Ȯ��(true: �α���, false: �α��� X)


	static void onSystem() throws IOException
	{	
		on = true;
		login = false;

		while (on && !login)
		{
			memCheck();					// on �� true  : ������ �α��� ���� �� �̿��� ��� ��� ����ؾ� ��
										//	  �� false : ������ �α��� ���� �� �̿��� ��� ���� ����!

										// login �� true : memCheck() ���� �α��� ����
			// �̿��� ��� ����
			while (login)
			{
				menuDisp();
				menuSelect();
				menuRun();				// �α׾ƿ� ���� �� login = false;
			}	
		}
	}
	

	// ========================== �α��� ���� �޼ҵ� ==========================

	// ȸ�� Ȯ�� �޼ҵ�
	static void memCheck() throws IOException
	{
		System.out.println();
		System.out.println("�� ȸ���̽ʴϱ�? (Y/N)");
		System.out.println("(������ ���: A)");

		do
		{
			System.out.print(">> ");
			con = br.readLine().toUpperCase();
			
			if (!(con.equals("Y") || con.equals("N") || con.equals("A")))
				System.out.println("�߸� �Է��ϼ̽��ϴ�.");
		}
		while (!(con.equals("Y") || con.equals("N") || con.equals("A")));

		switch(con)
		{
			case "Y" : login();	break;
			case "N" : join();	break;
			case "A" : on = !AdSystem.login();	break;	
						// AdSystem.login() �� ������ ��� �α��ο� ������ ��� true ��ȯ�ϴ� �޼ҵ�
						// on �� true  : ������ �α��� ���� �� �̿��� ��� ��� ����ؾ� ��
						//	  �� false : ������ �α��� ���� �� �̿��� ��� ���� ����!
		}												
	}


	// ���� �α���
	static void login() throws IOException
	{
		String tempId, tempPw;	//-- ����ڷκ��� �Է¹��� ��
		String rightPw;			//-- ���� PW�� ������ ����

		System.out.println();
		System.out.println("<< �̿��� �α��� >>");

		// ���̵� Ȯ��
		do
		{
			System.out.print("�� ���̵� : ");
			tempId = br.readLine();

			if (!Library.memList.containsKey(tempId))
			{
				System.out.println("���̵� �������� �ʽ��ϴ�.");
				
				do
				{
					System.out.println("(�ٽ� �α���: Y, ���� ȭ������: N");					
					System.out.print(">> ");	
					con = br.readLine().toUpperCase();
					if ( !(con.equals("Y") || con.equals("N")) )
						System.out.println("�߸� �Է��ϼ̽��ϴ�.");
				}
				while (!(con.equals("Y") || con.equals("N")));

				if (con.equals("N"))
					return;
			}
		}
		while (!Library.memList.containsKey(tempId));
		

		// �н����� Ȯ��
		do
		{
			System.out.print("�� ��й�ȣ : ");
			tempPw = br.readLine();

			rightPw= (Library.memList.get(tempId)).getPw();

			if (!tempPw.equals(rightPw))
			{
				System.out.println("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");

				do
				{
					System.out.println("(�ٽ� �α���: Y, ���� ȭ������: N");	
					System.out.print(">> ");
					con = br.readLine().toUpperCase();
					if ( !(con.equals("Y") || con.equals("N")) )
						System.out.println("�߸� �Է��ϼ̽��ϴ�.");
				}
				while (!(con.equals("Y") || con.equals("N")));

				if (con.equals("N"))
					return;
			}
		}
		while (!tempPw.equals(rightPw));


		// �α��� ����
		System.out.println(tempId + "�Բ��� �α����Ͽ����ϴ�.");
		nowId = tempId;			
		login = true;
	}


	// ȸ������
	static void join() throws IOException
	{		
		System.out.println();
		do
		{			
			System.out.println("ȸ�������Ͻðڽ��ϱ�? (Y/N)");
			System.out.print(">> ");
			con = br.readLine().toUpperCase();
			if ( !(con.equals("Y") || con.equals("N")) )
						System.out.println("�߸� �Է��ϼ̽��ϴ�.");
		}
		while (!(con.equals("Y") || con.equals("N")));
		
		if (con.equals("Y"))
			makeNewMem();		
	}


	// ���ο� ������ �����ϴ� �޼ҵ�
	private static void makeNewMem() throws IOException
	{
		String newId, newPw, newName, newSsn;		//-- ����ڷκ��� �Է� ���� ������

		// ���̵� ����
		System.out.println();
		do
		{			
			System.out.print("�� ���̵� �Է� : ");
			newId = br.readLine();

			if (Library.memList.containsKey(newId))
				System.out.println("�ߺ��� ���̵��Դϴ�!");
		}
		while (Library.memList.containsKey(newId));
		

		// ��й�ȣ �Է�
		System.out.print("�� ��й�ȣ �Է� : ");
		newPw = br.readLine();


		// ���� �Է�
		System.out.println();
		System.out.println("�� ȸ�� ���� �Է�");		
		System.out.print("�̸� : ");
		newName = br.readLine();

		do
		{
			System.out.print("�ֹε�Ϲ�ȣ : ");
			newSsn = br.readLine();
			if (!checkSsn(newSsn))	// ��ȿ�� �˻� �޼ҵ�
				System.out.println("�߸��� �ֹε�Ϲ�ȣ�Դϴ�!");
		}
		while (!checkSsn(newSsn));


		// �ڷᱸ���� ���� �Է�
		Library.memList.put(newId, new Members(newPw, newName, newSsn));		
		System.out.println("\nȸ������ �Ϸ�!");
	}


	// �ֹε�Ϲ�ȣ ��ȿ�� �˻� �޼ҵ�
	private static boolean checkSsn(String str)
	{
		// �ֿ� ���� ����
		int[] chk = {2, 3, 4, 5, 6, 7, 0, 8, 9, 2, 3, 4, 5};	// �������� ��		
		int tot = 0;			// ���� ���� �� �������� ���� ����
		int su;					// ���� ���� ����� ���� ����
		
		// �� �Էµ� �ֹε�Ϲ�ȣ �ڸ��� Ȯ��
		if (str.length() != 14)
			return false;

		// ����ȿ�� �˻�
		for (int i=0; i<13; i++)
		{
			if (i==6)	// 7��° �ڸ�("-")�� ���� ����
				continue;
			tot += chk[i] * Integer.parseInt(str.substring(i, i+1));
		}

		su = 11 - tot % 11;
		su = su % 10;
		// --==>> ���� ���� ����� ���� su �� ��� ��Ȳ�̴�.

		// ��� ��ȯ
		if (su==Integer.parseInt(str.substring(13)))
			return true;
		else
			return false;
	}



	// ============================= �α��� �� �ʿ� �޼ҵ� =============================



	// ���� �޴� ���÷���
	static void menuDisp()
	{
		System.out.println();
		System.out.println("<< �̿��� ���� ���� >>");
		System.out.println("1. ���� �˻�");
		System.out.println("2. ���� ����");
		System.out.println("3. ���� �ݳ�");
		System.out.println("4. ������� ��û");
		System.out.println("5. ����������");
		System.out.println("6. �ű� �԰� ���� ���");
		System.out.println("7. �α׾ƿ�");
	}

	// ���� �޴� ����
	static void menuSelect() throws IOException
	{
		do
		{	
			System.out.print(">> ");
			con = br.readLine();

			if (!(Integer.parseInt(con) >= 1 && Integer.parseInt(con) <= 7))
				System.out.println("�߸� �Է��ϼ̽��ϴ�.");
		}
		while (!(Integer.parseInt(con) >= 1 && Integer.parseInt(con) <= 7));		
	}
	

	// ���� �޴� ����
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


	// 1. �����˻��޴�
	static void selectSearchMenu() throws IOException
	{
		do
		{
			System.out.println();
			System.out.println("<< ���� �˻� >>");
			System.out.println("�˻� ��� ����");
			System.out.println("1. ������");
			System.out.println("2. ���ڸ�");
			System.out.println("3. ���ǻ��");
			System.out.println("4. ī�װ���");
			System.out.println("0. �̿��� ���� �������� �̵�");
			
			do
			{	
				System.out.print(">> ");
				con = br.readLine();

				if ( !(Integer.parseInt(con) >= 0 && Integer.parseInt(con) <= 4) )
					System.out.println("�߸� �Է��ϼ̽��ϴ�.");
			}
			while (!(Integer.parseInt(con) >= 0 && Integer.parseInt(con) <= 4) );		
			

			switch(con)
			{
				case "1" : searchTitle();			break;			// ������ �˻�
				case "2" : searchAuthor();			break;			// ���ڸ� �˻�
				case "3" : searchPublisher();		break;			// ���ǻ�� �˻�
				case "4" : searchCategory();		break;			// ī�װ��� �˻�
			}
		}
		while ( !con.equals("0")  );


	}

	// 1-1. ������ �˻�
	static void searchTitle() throws IOException 
	{
		System.out.print("������ �Է�: ");
		String title = br.readLine();
		System.out.println();

		if (Library.bookList.containsKey(title))		// �Է¹��� �������� Ű���� �����Ѵٸ�
		{
			System.out.printf("%10s %10s %10s %10s %10s %10s %10s \n", "������", "���ڸ�", "���ǻ�", "���ǳ⵵", "�з���ȣ", "ī�װ�", "���Ⱑ�ɿ���");
			System.out.println("=============================================================================================================");
			
			Enumeration e = Library.bookList.keys();		// �ϸ���Ʈ Ű ���� �޾ƿ���
			while (e.hasMoreElements())						
			{
				if (title.equals(e.nextElement()))			// �Է¹��� ������� ���� ��
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
		else											// �Է¹��� �������� Ű���� ����x
		{
			System.out.println("������ �������� �ʽ��ϴ�.");
			System.out.println("(��Ȯ�� �Է��ߴ��� Ȯ�����ּ���.)");
			System.out.println();
		}	
	}

	// 1-2. ���ڸ� �˻�
	static void searchAuthor() throws IOException 
	{
		System.out.print("���ڸ� �Է�: ");
		String author = br.readLine();
		System.out.println();
		String[] bookAuthors = new String[Library.bookList.size()]; // ���ڵ�
		int i=0;
		
		Enumeration e1 = Library.bookList.keys();
		while (e1.hasMoreElements())
		{
			String key = (String)e1.nextElement();
			Books book1 = Library.bookList.get(key);
			bookAuthors[i++] = book1.getAuthor();			// bookList ���ڵ��� bookAuthors �迭�� ����
		}
		
		if (Arrays.asList(bookAuthors).contains(author))	// ���ڵ鿡 �Է��� ���ڰ� �����Ѵٸ�
		{	
			System.out.printf("%10s %10s %10s %10s %10s %10s %10s \n", "������", "���ڸ�", "���ǻ�", "���ǳ⵵", "�з���ȣ", "ī�װ�", "���Ⱑ�ɿ���");
			System.out.println("=============================================================================================================");
			Enumeration e2 = Library.bookList.keys();		// �ϸ���Ʈ Ű ���� �޾ƿ���
			while (e2.hasMoreElements())						
			{
				
				String title = (String)e2.nextElement();		// Ű�� �Ѿ�鼭 title�� ���
				Books book2 = Library.bookList.get(title);		// vaulue Books�� book2�� ���(BooksŸ��)
				if (author.equals(book2.getAuthor()))			// �Է��� ���ڸ�� book2�� ���ڸ��� ���� �� 
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
			System.out.println("������ �������� �ʽ��ϴ�.");
			System.out.println("(��Ȯ�� �Է��ߴ��� Ȯ�����ּ���.)");
			System.out.println();
		}						
	}

	// 1-3. ���ǻ� �˻�
	static void searchPublisher() throws IOException	
	{
		System.out.print("���ǻ� �Է�: ");
		String publisher = br.readLine();
		System.out.println();
		String[] bookPublishers = new String[Library.bookList.size()]; // ���ǻ��
		int i=0;
		
		Enumeration e1 = Library.bookList.keys();
		while (e1.hasMoreElements())
		{
			String key = (String)e1.nextElement();
			Books book1 = Library.bookList.get(key);			
			bookPublishers[i++] = book1.getPublisher();			// bookList ���ǻ���� bookPublishers �迭�� ����
		}
		
		if (Arrays.asList(bookPublishers).contains(publisher))	// ���ǻ�鿡 �Է��� ���ǻ簡 �����Ѵٸ�
		{	
			System.out.printf("%10s %10s %10s %10s %10s %10s %10s \n", "������", "���ڸ�", "���ǻ�", "���ǳ⵵", "�з���ȣ", "ī�װ�", "���Ⱑ�ɿ���");
			System.out.println("=============================================================================================================");
			Enumeration e2 = Library.bookList.keys();		// �ϸ���Ʈ Ű ���� �޾ƿ���
			while (e2.hasMoreElements())						
			{
				String title = (String)e2.nextElement();		// Ű�� �Ѿ�鼭 title�� ���
				Books book2 = Library.bookList.get(title);		// vaulue Books�� book2�� ���(BookŸ��)
				if (publisher.equals(book2.getPublisher()))			// �Է��� ���ǻ��� book2�� ���ǻ���� ���� �� 
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
			System.out.println("������ �������� �ʽ��ϴ�.");
			System.out.println("(��Ȯ�� �Է��ߴ��� Ȯ�����ּ���.)");
			System.out.println();
		}	
	}


	// 1-4. ī�װ� �˻�
	static void searchCategory() throws IOException	
	{
		System.out.print("ī�װ� �Է�: ");
		String category = br.readLine();
		System.out.println();
		String[] bookCategorys = new String[Library.bookList.size()]; // ī�װ���
		int i=0;
		
		Enumeration e1 = Library.bookList.keys();
		while (e1.hasMoreElements())
		{
			String key = (String)e1.nextElement();
			Books book1 = Library.bookList.get(key);
			bookCategorys[i++] = book1.getCategory();			// bookList ī�װ����� bookPublishers �迭�� ����
		}
		
		if (Arrays.asList(bookCategorys).contains(category))	// ī�װ��鿡 �Է��� ī�װ��� �����Ѵٸ�
		{	
			System.out.printf("%10s %10s %10s %10s %10s %10s %10s \n", "������", "���ڸ�", "���ǻ�", "���ǳ⵵", "�з���ȣ", "ī�װ�", "���Ⱑ�ɿ���");
			System.out.println("=============================================================================================================");
			Enumeration e2 = Library.bookList.keys();		// �ϸ���Ʈ Ű ���� �޾ƿ���
			while (e2.hasMoreElements())						
			{
				String title = (String)e2.nextElement();		// Ű�� �Ѿ�鼭 title�� ���
				Books book2 = Library.bookList.get(title);		// vaulue Books�� book2�� ���(BookŸ��)
				if (category.equals(book2.getCategory()))			// �Է��� ī�װ���� book2�� ī�װ����� ���� �� 
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
			System.out.println("������ �������� �ʽ��ϴ�.");
			System.out.println("(��Ȯ�� �Է��ߴ��� Ȯ�����ּ���.)");
			System.out.println();
		}	
	}


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 2. ��������
	static void rentalBook() throws IOException
	{
		System.out.println();
		System.out.println("<< ���� ���� >>");

		if (overdue(nowId).equals("Y"))			// ��ü ���� "Y" �� ��
		{
			System.out.println("(" + nowId + ")" + "��ü�ᰡ �����մϴ�.");
			System.out.println("���� �ݳ� �� ������ �����մϴ�.");
			System.out.println();
			do
			{
					System.out.print("���� �ݳ��Ͻðڽ��ϱ�?(Y/N) : ");;	
					con = br.readLine().toUpperCase();
					if ( !(con.equals("Y") || con.equals("N")) )
						System.out.println("�߸� �Է��ϼ̽��ϴ�.");
			}
			while (!(con.equals("Y") || con.equals("N")));

			if (con.equals("Y"))			// �ݳ��� ��
				returnBook();
			else	//if (con.equals("N")) 	// �ݳ����ϱ� �����ؼ� ���θ޴���
			{
				System.out.println("���� ������ �����մϴ�.");
				return;
			}
		}
		else									// ��ü ���� "N" �� ��
		{
			System.out.print("������ ���� : ");
			String title = br.readLine();

			if (Library.bookList.containsKey(title))		// �Է¹��� �������� Ű���� �����Ѵٸ�
			{
				do
				{
						System.out.print("���� �����Ͻðڽ��ϱ�?(Y/N) : ");
						con = br.readLine().toUpperCase();
						if ( !(con.equals("Y") || con.equals("N")) )
							System.out.println("�߸� �Է��ϼ̽��ϴ�.");	
				}
				while (!(con.equals("Y") || con.equals("N")));

				System.out.println();

				if (con.equals("Y")) // ���� ����
				{	
					Members mem = Library.memList.get(nowId);		// ���� �α��ε� ���̵��� value

					//mem.getRentalBook() => ���� �α��ε� ���̵��� �������� å ��
					if (mem.getRentalBook() < Library.RENT_BOOKS)		// ���� �������� å ���� ���Ⱑ�� å �� ���� ���� ��	
					{
						Library.bookList.get(title).setRental(false);	// �Է��� å ��ü�� ���⿩�ο� false(������) �־���

						Calendar today = new GregorianCalendar();			// ���� ��¥ �޾ƿ�
						int y1,m1,d1;
						y1 = today.get(Calendar.YEAR);
						m1 = today.get(Calendar.MONTH);
						d1 = today.get(Calendar.DATE);
						Library.rentList.add(new RentalInfo(y1, m1, d1, title, nowId));	// rentList(������Ȳ)�� �������� �߰�
						mem.setRentalBook(mem.getRentalBook() + 1);		// ������̵� ��ü�� ������ å �� ����

						Calendar cal = new GregorianCalendar();
						int y2,m2,d2;
						cal.add(Calendar.DATE, Library.RENT_DAYS);		// 14�� ���� ��¥
						y2 = cal.get(Calendar.YEAR);
						m2 = cal.get(Calendar.MONTH);
						d2 = cal.get(Calendar.DATE);

						System.out.printf("%s ������ �Ϸ�Ǿ����ϴ�.\n", title);
						System.out.printf("�뿩�Ⱓ�� (%d-%d-%d)����(%d��) �Դϴ�.\n", y2, (m2+1), d2, Library.RENT_DAYS);
						System.out.println();
					}
					else	// ���� �������� å ���� ���Ⱑ�� å ���� ������...!! (�ʰ��� �� ����)
					{
						System.out.println("5�� �̻� ���� �� �� �����ϴ�. �ݳ� �� �����ϼ���.");
					}
				}
				else	//if (con.equals("N")) //�������� ���Ҷ�
				{
					System.out.println("���� ������ �����մϴ�.");
					return;
				}
					
			}
			else											// �Է¹��� �������� Ű���� ����x
			{
				System.out.println();
				System.out.println("�������� �ʴ� �����Դϴ�.");
				
			}
		}
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	// 3. �����ݳ�
	public static void returnBook() throws IOException
	{
		System.out.println("\n<< ���� �ݳ� >>");
		int bookNum = 0;						// �ݳ��� �Ǽ� ���� ����
		String[] tempStr;						// �Ǽ� ��ŭ ����ڰ� �Է��ϴ� �������� ��Ƶ� �迭
		Members ob = Library.memList.get(nowId);		// nowId�� Members��ü ��������

		System.out.print("�ݳ� �Ǽ� : ");
		bookNum = Integer.parseInt(br.readLine());
		if(bookNum ==0)
		{
			System.out.println("[���� �ݳ� ����]");
			return;
		}
		else if (bookNum > ob.getRentalBook())
		{
			System.out.println(nowId + "���� �ݳ��� ������ " + bookNum + "�� �̸��Դϴ�.");
			return;
		}
		else if (ob.getRentalBook() == 0)
		{
			System.out.println("�������� å�� �����ϴ�.");
			return;
		}

		tempStr = new String[bookNum];			// �Ǽ���ŭ �迭�� ����
		for (int i=0; i<bookNum ; i++)			// ������ �Է¹޾� �迭�濡 ���
		{
			System.out.printf("������(%d) : ", i+1 );
			tempStr[i] = br.readLine();
		}
		System.out.println();
		
		int n=0;
		for (int i=0; i<bookNum ; i++)
		{
			for ( RentalInfo e : Library.rentList )			
			{	
				if ( ( (e.getRBook()).equals(tempStr[i]) ) && ( (e.getRMem()).equals(nowId) ) && (!e.isReturnBook()) )
					n++;														// �������������Ʈ�� ���鼭 ȸ���� ������ ��ġ�ϰ� ���ÿ�	
			}																	// �ݳ����ڰ� ���ŵ��� ���� ��, �ݳ����� ���� ������ �ִٸ�
		}																		// n++;
		
		if (n < bookNum)				// �ѱ��̶� ��ġ�ϴ� ���� ��ã����.. �Ұ���
		{
			System.out.println("[��ġ�ϴ� ���� ���� ��ȸ �Ұ�]");	
			return;
		}			
		
		//----------------------------- ������� �޼ҵ尡 ������� �ʾҴٸ� �Է¹��� �ݳ��� ������ ��� �����ϴ� ��..-------------------------
		
		
		
		if ( overdue(nowId).equals("N") )									// ��ü���� x
		{
			ob.setRentalBook( ob.getRentalBook() - bookNum );			// nowId�� Members��ü�� ���� ���� �Ǽ��� �ݳ��� �Ǽ���ŭ �ٿ��ֱ�
			
			updateRentalInfo(tempStr);									// �ݳ��� �������� ���� ���� ��ü�� ������Ʈ
			
			System.out.println("�ݳ��� �Ϸ�Ǿ����ϴ�.");
			
		}
		else											// ��ü���Τ�						
		{
			System.out.println("��ü�ᰡ �����մϴ�.");
			int k =0;				// for ���� ���� ��������
			int lateFeeTot = 0;		// �� å�� ��ü�Ḧ ���� ������ ���� => �󸶳����ϴ��� ����ڿ��� �����ϱ� ����

			for ( RentalInfo e : Library.rentList )
			{
				
				if ( ( (e.getRBook()).equals(tempStr[k]) ) && ( (e.getRMem()).equals(nowId) ) &&  (!e.isReturnBook()) )
				{
					System.out.printf("�� %s(%d�� ��ü)%,8d��\n", tempStr[k], calLateDays(e), calLateFee(e) );
					lateFeeTot += calLateFee(e);
					k++;
				}
				if(k==bookNum)
					break;
			}

			System.out.printf("=> %,d ��\n", lateFeeTot);	

			do
			{
				System.out.print("��ü�� ����(Y/N) : ");
				con = br.readLine().toUpperCase();
				if ( (!con.equals("Y")) && (!con.equals("N")) )
					System.out.println("�߸� �Է��Ͽ����ϴ�.");
			}
			while ( (!con.equals("Y")) && (!con.equals("N")) );
			
			System.out.println();
			if (con.equals("Y"))
			{
				System.out.printf("����ü �ݾ� : %d\n", lateFeeTot);
				System.out.println("[������ �ݾ� �Է�(������ ����)]");

				int[] kindsOfMoney = {10000, 5000, 1000, 500, 100};			// ������ �迭 ����	
				int tempM[] = new int[5];									// ������ �Է� ���� ���� �迭								
				int tempTot = 0;											// �Է¹��� ���� ���մ��� ����

				for (int i=0; i<kindsOfMoney.length ; i++)					// �켱 ������ �Է��� ����(�Է¸�)	
				{
					System.out.printf("%,d�� : ", kindsOfMoney[i]);
					tempM[i] = Integer.parseInt(br.readLine());
					tempTot += ( tempM[i]*kindsOfMoney[i] );
				}
				System.out.println("===========================");
				System.out.printf("������ �ݾ� : %,d\n", tempTot );
				System.out.println();

				if(tempTot >= lateFeeTot)									// ������ �Է��� ���� ������ �� �����ϴ� ��ü��� ���ų� ũ�ٸ�
				{
					if (tempTot-lateFeeTot == 0)							// ������ �Ž����� �� �ʿ�x
					{
						for (int i=0; i<5; i++)									// Library.money �迭�� �Է¹��� ������ ���� ������Ʈ
							Library.money[i] += tempM[i];

						ob.setRentalBook( ob.getRentalBook() - bookNum );	// nowId�� Members��ü�� ���� ���� �Ǽ��� �ݳ��� �Ǽ���ŭ �ٿ��ֱ�
						updateRentalInfo(tempStr);							// �ݳ��� �������� ���� ���� ��ü�� ������Ʈ	
					}
					else													// ���� ���� �� ������ �Ž����� �� �ʿ䤷
					{
						String result = returnChange(tempTot-lateFeeTot);
						if (result.equals("�Ž����� : " + (tempTot-lateFeeTot) + "��") )		// �Ž������� �������� �ʴٸ�
						{
							for (int i=0; i<5; i++)									// Library.money �迭�� �Է¹��� ������ ���� ������Ʈ
								Library.money[i] += tempM[i];

							System.out.println(result + "\n");
							ob.setRentalBook( ob.getRentalBook() - bookNum );	// nowId�� Members��ü�� ���� ���� �Ǽ��� �ݳ��� �Ǽ���ŭ �ٿ��ֱ�
							updateRentalInfo(tempStr);							// �ݳ��� �������� ���� ���� ��ü�� ������Ʈ	
						}
						else if (result.equals("�ܵ��� �����մϴ�.") )				// �Ž������� �����ϴٸ�
						{
							System.out.println(result + "\n���� �ݳ��� �����Ͽ����ϴ�.\n(�����ڿ��� ���ǹٶ�)");
							return;													// �����ߴٰ� �ȳ� �� �޼ҵ� ����
						}
					}			
					System.out.println("��ü�� ���� �� ���� �ݳ��� ��� �Ϸ�Ǿ����ϴ�.");	
				}
				else															// ������ �Է��� ���� ������ �� �����ϴ� ��ü�Ẹ�� �۴ٸ�
				{	
					System.out.println("�Է��� �ݾ��� ��ü�Ẹ�� ������ ���� �����Ͽ����ϴ�.");		// �ݾ� �������� ���� ���� �ȳ� �� �޼ҵ� ����
					return;
				}
			}
			else if (con.equals("N"))
			{
				System.out.println("[��ü�� ���� �ź�]\n���� �ݳ��� �����մϴ�.");
			}
			
			System.out.println();
		}
			
	}


	// �����迭(ȸ���� �ݳ��Ϸ��� ������ ���� ������ŭ�� �迭���� ����)�� �޾� 
	// ���� ���� ��ü�� ������Ʈ ���ְ�
	// �� �������� ���Ⱑ�� ���·� ������Ʈ ���ִ� �޼ҵ�
	private static void updateRentalInfo(String[] tempStr)
	{
		Calendar rightNow = Calendar.getInstance();
		int y = rightNow.get(Calendar.YEAR);
		int m = rightNow.get(Calendar.MONTH);
		int d = rightNow.get(Calendar.DATE);

		for (int i=0; i< tempStr.length ; i++)							// �� ������ü�� ���� true���Ⱑ������ ����
			Library.bookList.get(tempStr[i]).setRental(true);

		int i=0;		// for ���� ���� �������� - ���� �Ǽ� ��ŭ ��ü�� ������Ʈ �������.
		for ( RentalInfo e : Library.rentList )
		{
			if (i==tempStr.length)
				break;
			if ( ( (e.getRBook()).equals(tempStr[i]) ) && ( (e.getRMem()).equals(nowId) )  && (!e.isReturnBook()) )	
			{																			// �� �����̷¿��� ȸ���� ������ ��ġ�ϸ鼭
				System.out.println(tempStr[i]);											// �ݳ����ڰ� ���ŵ��� ���� ��, �ݳ����� ����!
				e.setReturnDate(y, m, d);			// �ݳ��� ���� 1��1��1�� -> ���� ��¥��
				e.setReturnBook(true);				// �ݳ����� -> true
				e.setLateFee(calLateFee(e) );		// ��ü��
				i++;
			}
		}
	}

	// �Ž����� ���
	private static String returnChange(int change)
	{
		String result ="";
		int[] kindsOfMoney = {10000, 5000, 1000, 500, 100};	
		int tempM[] = new int[5];							// �Ž������� �ֱ����� �������� �ʿ��� ���� ���� �迭
		int tempChange = change;							// �ؿ��� changd�� �ʿ��ϹǷ� tempChange�� ��Ƽ� ���

		for (int i=0; i<5 ; i++)							// ��迡 �Ž��������� ����� �ܵ� �� ��������(����) Ȯ��
		{
			int temp = tempChange / kindsOfMoney[i];		// ��
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

		if (tempSum == change)								// ��迡 �ܵ��� �� ������ �������� �ʴٸ�
		{
			for (int i=0; i<5 ; i++)						// ��谡 �������ִ� ������ �ܵ� ������ �����Ͽ�(����) �Ž����� ó��
			{
				Library.money[i] -= tempM[i];
			}
			result = String.format("�Ž����� : %,d��", change);
		}
		else
			result = "�ܵ��� �����մϴ�.";		// ��迡 �ܵ��� �� �����̶� �����ϴٸ� �Ž����� ó��x 

		return result;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	// 4. ������� ��û
	static void requestBook() throws IOException
	{
		//å ��� �Է°� �ޱ�
		System.out.println("<<��� ���� ��û>>");
		System.out.print("������ : ");      
		String title = br.readLine();      
		System.out.print("���ڸ� : ");      
		String author = br.readLine();
		System.out.println();

		Members mem = Library.memList.get(nowId);   // ���� ���̵� Ű������ �ϴ� Value(Members Ÿ��) ������
		String name = mem.getName();				// ��û�ڸ� (���� �α��ε� ȸ���� �̸�)

		Calendar cal = Calendar.getInstance();
		int y,m,d;      // ���� �� �� ��
		y = cal.get(Calendar.YEAR);      //��
		m = cal.get(Calendar.MONTH);   //��   (+1) => �̰� �����൵ �ſ�! ����Ҷ��� ���ָ� ��!
		d = cal.get(Calendar.DATE);      //�� 

		if (Library.bookList.containsKey(title))		// ��û�Ϸ��� ������ �̹� �����ϸ�    
		{
			System.out.println("�̹� �����ϴ� �����Դϴ�!");
			return;
		}
		else											// ��û�Ϸ��� ������ ����x
		{    
			do
			{
				System.out.printf("%s ��û�Ͻðڽ��ϱ�?(Y/N) : ",title);
				con = br.readLine().toUpperCase();
				if ( !(con.equals("Y") || con.equals("N")) )
					System.out.println("�߸� �Է��ϼ̽��ϴ�.");	
			}
			while (!(con.equals("Y") || con.equals("N")));

			if(con.equals("Y"))
			{
				Library.wishList.add(new Wish(title, author, name, y, m, d));   //wishList�ڷᱸ��
				System.out.println("��û�� �Ϸ� �Ǿ����ϴ�.");
			}
			else
				return;

		}
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	// 5. ����������
	static void selectMypage() throws IOException
	{
		do
		{
			System.out.println();
			System.out.println("<< ���������� >>");
			System.out.println("1. �� ����");
			System.out.println("2. ���� ���� ��Ȳ");
			System.out.println("3. ���� ��� ���� ��û ��Ȳ");
			System.out.println("0. �̿��� ���� �������� �̵�");
	   
			do
			{
			System.out.println();
			System.out.print(">> ");
		  
			con = br.readLine();

				if (!(Integer.parseInt(con) >= 0 && Integer.parseInt(con) <= 3) )
					System.out.println("�߸� �Է��ϼ̽��ϴ�.");
			}
			while (!(Integer.parseInt(con) >= 0 && Integer.parseInt(con) <= 3) ); 

			switch(con)
			{
				case "1" : myInfo();				break;		// �� ����
				case "2" : myRentalStatus();		break;		// ���� ���� ��Ȳ
				case "3" : myWishBookStatus();		break;		// ���� ��� ���� ��û ��Ȳ
			}
		}
		while ( !con.equals("0") );


	}

	
	// 5-1. �� ����
	static void myInfo() throws IOException  
	{      
		System.out.println();
		System.out.println("<< �� ���� >>");
      
		Enumeration e = Library.memList.keys();
      
		//������̵�� memList�� key(id)�� ��ġ�ϴ��� Ȯ���ϰ� ���̵�, �̸�, �ֹι�ȣ ������� �̴´�.
		while (e.hasMoreElements())
		{
			if ( nowId.equals(e.nextElement()) )   //������̵�� ������̵��� ���� Ű���� ��
			{
				System.out.print("�� ���̵� : " + nowId);    
				System.out.print("\n�� �̸� : " + Library.memList.get(nowId).getName());
				System.out.print("\n�� �ֹι�ȣ : " + Library.memList.get(nowId).getSsn());
				System.out.println();
			}
		}
	}


	// 5-2. ���� ���� ��Ȳ
	static void myRentalStatus() throws IOException
	{
		int c = 0; 
		ListIterator<RentalInfo> r = Library.rentList.listIterator();	
		while (r.hasNext())		
		{
			RentalInfo ren = r.next();	
			if( (nowId.equals(ren.getRMem())) && (!ren.isReturnBook()) )	// ���̸� + �ݳ����� ���� å -> �Ǽ�
				c++;
		}
		
		System.out.println();
		System.out.println("<< ���� ���� ��Ȳ >>");
		System.out.println();
		System.out.print("�� ���� �Ǽ� : " + c);
		System.out.println("\n�� ������ ����\n");
		System.out.println("    ������      ���� ����       ���� ����         ��ü����      ��ü�ϼ�      ��ü��");
		System.out.println("======================================================================================");
		
		String [] ids = new String [Library.rentList.size()];
		int i =0;
		
		// ids �迭�� ȸ���� ������̵� ��� ��
		ListIterator<RentalInfo> r1 = Library.rentList.listIterator();
		while (r1.hasNext())
		{
			RentalInfo ren2 = r1.next();
			ids[i++] = ren2.getRMem();
		}

		if (Arrays.asList(ids).contains(nowId))	// ids��� �迭�� ArrayList�� �ٲ� ��ȯ�ϴ� �޼���
		{		
			ListIterator<RentalInfo> r2 = Library.rentList.listIterator();
			while (r2.hasNext())	//�ش� ���ͷ��̼�(iteration)�� ���� ��Ҹ� ������ ������ true�� ��ȯ�ϰ�, �� �̻� ���� ��Ҹ� ������ ���� ������ false�� ��ȯ��	
			{
				RentalInfo ren = r2.next();	//���ͷ��̼�(iteration)�� ���� ��Ҹ� ��ȯ��.
				if(( nowId.equals(ren.getRMem()) ) && ( !ren.isReturnBook() ))	// ȸ���� ������̵� ������̵�� ���� �ݳ��� �����ȵǾ����� ���
				{	
					Calendar cal = new GregorianCalendar();       
					int y = ren.getRentalDate().get(Calendar.YEAR);
					int m = ren.getRentalDate().get(Calendar.MONTH);
					int d = ren.getRentalDate().get(Calendar.DATE);
					cal.set(y,m,d);
					cal.add(Calendar.DATE, Library.RENT_DAYS);	//-- ���� ��¥  + 14

					// �� ���� �ݳ� ���� ���
					Calendar today = new GregorianCalendar();      //-- ����     
					long tempR = today.getTimeInMillis() - cal.getTimeInMillis();	// ���� - �ݳ�������
					if (tempR<=0)	//�ݳ� �������� �̷��� �� ���� ���̳ʽ� ����
					{
						long sec = (cal.getTimeInMillis() - today.getTimeInMillis())/ 1000;   // '�ݳ�������(�̷�) - ����' �� ��

						// �԰�¥�� ���糯¥�� ���̸� �� ������ ��ȯ
						// �� 1�� = 24(�ð�) * 60(��) * 60(��)
						//     ����, sec ������ 24*60*60 �� ���� ������ �� ���� �Ϸ� ��ȯ ����
						long days = sec / (24*60*60);
						int result = (int)days;		//-- ���� ����

						System.out.printf("%8s %10s %10d�� %8s %8d %9d\n",ren.getRBook(), ren.getRentalDateStr()
															,result, overdue(nowId)
															, calLateDays(ren), calLateFee(ren));
					}
					else if(tempR>0)	// �ݳ� �������� �̹� �������� �� - ��ü�߻�
					{
						System.out.printf("%8s %10s %10d�� %8s %8d %9d\n",ren.getRBook(), ren.getRentalDateStr(),
															0, overdue(nowId)
															, calLateDays(ren), calLateFee(ren));
					}
				}
			}
		}
		else
			System.out.println("������ �̷��� �����ϴ�.");

	}


	// 5-3. ���� ��� ���� ��û ��Ȳ
	static void myWishBookStatus() throws IOException
	{
		Members mem = Library.memList.get(nowId);	// Menbers Ÿ�� mem�� ���� ���̵� ��´�   
		String name = mem.getName();				// String name �� ���� ���̵��� ȸ���̸�(value���ȿ�)�� ��´�
		String [] names = new String [Library.wishList.size()];
		int i = 0;

		System.out.println();
		System.out.println("<< ���� ��� ���� ��û ��Ȳ >>");
		System.out.println();

		ListIterator<Wish> li2 = Library.wishList.listIterator();
		while (li2.hasNext())
		{
			Wish wish2 = li2.next();		// wish�� �ִ� ���� ��Ҹ� ��ȯ
			names [i++] = wish2.getWMem();	// ��û�̸����� ����
		}

		if (Arrays.asList(names).contains(name))  // ���ڿ��� ���ԵǾ��ִ��� ����  contains�� ���� �� 
		{
			System.out.printf("%10s%10s%10s%10s", "������", "���ڸ�", "��û ����", "��û ����");
			System.out.println("\n===========================================================");
         
			ListIterator<Wish> li = Library.wishList.listIterator(); 
			while (li.hasNext())
			{
				Wish wish = li.next(); 
				if (wish.getWMem().equals(name))  //���ÿ��� ��û�ڸ� �����ͼ� ��û�ڸ��̶� ����α��ε�ȸ���� �̸��� ������
				{
					System.out.printf("%7s %5s %12s %12s\n"
										, wish.getWTitle(), wish.getWAuthor()                                    
										, wish.getReqDateStr(), wish.getRequestStr());  
				}
			}
		}
		else
			System.out.println("��û�Ͻ� ��������� �����ϴ�.");

		System.out.println();
	}



/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	

	// �α� �ƿ�
	static void logout()
	{
		System.out.println("�α׾ƿ��մϴ�.");
		login = false;
	}

}
