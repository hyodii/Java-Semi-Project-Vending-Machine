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
	// final ���� ����
	static final int RENT_BOOKS = 5;		// �뿩 ������ å�� �Ǽ�: 5��
	static final int RENT_DAYS = 14;		// ���� �Ⱓ: 14��
	static final int LATE_FEE = 300;		// 1�ϴ� ��ü��: 300��
	
	// �ڷᱸ�� ����
	public static Hashtable<String, Members> memList = new Hashtable<String, Members>();
	public static Hashtable<String, Books> bookList = new Hashtable<String, Books>();
	public static ArrayList<RentalInfo> rentList = new ArrayList<RentalInfo>();
	public static ArrayList<Wish> wishList = new ArrayList<Wish>();

	// �迭 ����
	public static int[] money = new int[5];		//-- ������� 1����, 5õ��, 1õ��, 5���, 1��� �� ����
	
	// �׽�Ʈ �ڷ� �Է�
	public static void inputTestData()
	{
		// memList �׽�Ʈ ��
		memList.put("qwer1234", new Members("1234", "������", "990506-2055511")); // ��üO
		memList.put("zxc1111", new Members("1111", "����ȭ", "950201-2033211")); // ��üO
		memList.put("sdf2222", new Members("2222", "�ּ���", "961224-2012342")); // ��üX
		memList.put("asdf5678", new Members("5678", "�Ǽ���", "971007-1055522")); // 2��üX

		// bookList �׽�Ʈ ��
		bookList.put("����� �µ�", new Books("�̱���", "������", "2016", "��-46-13", "������", 2017, 10, 2));			//��������O 
		bookList.put("������ ����", new Books("�̱���", "������", "2014", "��-12-32", "������", 2015, 5, 11));			//��������O
		bookList.put("������ �ູ", new Books("������", "���೪��", "2013", "��-3-25", "�Ҽ�", 2018, 8, 15));			//��������O
		bookList.put("���", new Books("���Ͽ� ����", "����", "1999", "ī-5-13", "�Ҽ�", 2002, 11, 23));				//��������O 
		bookList.put("���ݼ���", new Books("�Ŀ�� �ڿ���", "���е��� ", "2001", "��-2-11", "�Ҽ�", 2003, 3, 20));		//��������X  
		bookList.put("28", new Books("������", "���೪��", "2011", "��-5-44", "�Ҽ�", 2021, 7, 2));						//�Ű�����O
		bookList.put("7���� ��", new Books("������", "���೪��", "2013", "��-5-28", "�Ҽ�", 2021, 6, 30));				//�Ű�����O 
		bookList.put("������ �ð�", new Books("����", "�ѱ��", "2021", "��-55-22", "��ġ", 2021, 6, 29));              //�Ű�����O 
		bookList.put("���ΰ� �ٴ�", new Books("���", "������", "2012", "��-12-3", "����", 2021, 7, 21));				//�Ű�����X
		bookList.put("�̱��� ������", new Books("��ó�� ��Ų��", "������ȭ��", "2018", "��-5-1", "����", 2021, 7, 1));  //�Ű�����X 
		

        // rentList �׽�Ʈ �� [�ݳ� �̷� �����ϴ� å��]
		rentList.add(new RentalInfo(2004, 5, 18, "���", "zxc1111"));				
		rentList.add(new RentalInfo(2017, 4, 21, "������ ����", "qwer1234"));		
		rentList.add(new RentalInfo(2018, 5, 4, "����� �µ�", "qwer1234"));	
		rentList.add(new RentalInfo(2021, 6, 31, "������ �ð�", "sdf2222"));			
		rentList.add(new RentalInfo(2021, 7, 3, "28", "zxc1111"));					
		rentList.add(new RentalInfo(2021, 7, 18, "7���� ��", "asdf5678"));			
	
		
		// ���⿩�� (���� ������)
		bookList.get("���").setRental(false);	
		memList.get("zxc1111").setRentalBook(memList.get("zxc1111").getRentalBook() + 1); 
		bookList.get("������ ����").setRental(false);
		memList.get("qwer1234").setRentalBook(memList.get("qwer1234").getRentalBook() + 1); 
		bookList.get("����� �µ�").setRental(false);	// �������� ������ ����
		memList.get("qwer1234").setRentalBook(memList.get("qwer1234").getRentalBook() + 1); // �������� å �� +1
		bookList.get("������ �ð�").setRental(false);	
		memList.get("sdf2222").setRentalBook(memList.get("sdf2222").getRentalBook() + 1); 
		bookList.get("28").setRental(false);	
		memList.get("zxc1111").setRentalBook(memList.get("zxc1111").getRentalBook() + 1); 
		bookList.get("7���� ��").setRental(false);	
		memList.get("asdf5678").setRentalBook(memList.get("asdf5678").getRentalBook() + 1); 

		// ������ �� �߿�  �ݳ�
		// å ����      ��������        �ݳ�����      ��ü
		// ���		     2004/6/18      2004/6/24      N		// ����	
		// ������ ����   2017/5/21      2017/7/15      Y
        // ����� �µ�   2018/6/4       2018/8/20      Y			
		// ������ �ð�   2021/7/31      2021/8/20      Y		// �Ű�
		// 28			 2021/8/3       2021/8/18	   Y		
        // 7���� ��	     2021/8/18      2021/8/19      N
		Iterator<RentalInfo> i = rentList.iterator();
		while (i.hasNext())
		{
			RentalInfo rent = i.next();
			String title = rent.getRBook();			// �������� å�̸�

			if ("���".equals(title))		// ��ü x
			{
				// ù ��° �ݳ�
				rent.setReturnDate(2004, 5, 24);	// �ݳ��� ����
				rent.setReturnBook(true);			// �ݳ� o
				
				
				rent.setLateFee(0);				// ��ü�� ����(��ü������ 0�� ����)

				String id = rent.getRMem();			// �ݳ��� ȸ�� ���̵�
				Members mem = memList.get(id);		// �ݳ��� ȸ�� ����
				mem.setRentalBook(mem.getRentalBook() - 1);	// ���� �������� å �� -1

				Books book = bookList.get(title);
				book.setRental(true);			// �ݳ��Ǽ� ���Ⱑ������ ����	
												
				
			}

			if ("������ ����".equals(title))		// ��ü o
			{
				rent.setReturnDate(2017, 6, 15);	// �ݳ��� ����
				rent.setReturnBook(true);			// �ݳ� o
				

				Calendar cal = Calendar.getInstance();      //-- �ݳ� �������� ���� ���� ����

				int y = rent.getRentalDate().get(Calendar.YEAR);
				int m = rent.getRentalDate().get(Calendar.MONTH);
				int d = rent.getRentalDate().get(Calendar.DATE);
				cal.set(y,m,d);								 // ������ ��¥ ����   
				cal.add(Calendar.DATE, Library.RENT_DAYS);   // ������ ��¥ + ������� = �ݳ� ������

				Calendar cal2 = new GregorianCalendar(2017, 6, 15);		//-- �ݳ��� ���ڸ� ���� ���� ����

				// ������ �ݳ����� - �ݳ� ������(��ü�ᰡ ����� ��¥)
				long sec = (cal2.getTimeInMillis() - cal.getTimeInMillis())/ 1000;
				
				long days = sec / (24*60*60);
				
				int result = (int)days;

				int fee = result*LATE_FEE;				// ��ü�ϼ� * 300(1�� ��ü��)
				rent.setLateFee(fee);				// ��ü�� ����

				String id = rent.getRMem();			// �ݳ��� ȸ�� ���̵�
				Members mem = memList.get(id);		// �ݳ��� ȸ�� ����
				mem.setRentalBook(mem.getRentalBook() - 1);	// ���� �������� å �� -1

				Books book = bookList.get(title);
				book.setRental(true);			// �ݳ��Ǽ� ���Ⱑ������ ����
				
			}

			if ("����� �µ�".equals(title))		// ��ü o
			{
				rent.setReturnDate(2018, 7, 20);	// �ݳ��� ����
				rent.setReturnBook(true);			// �ݳ� o
				
				Calendar cal = Calendar.getInstance();      //-- �ݳ� �������� ���� ���� ����

				int y = rent.getRentalDate().get(Calendar.YEAR);
				int m = rent.getRentalDate().get(Calendar.MONTH);
				int d = rent.getRentalDate().get(Calendar.DATE);
				cal.set(y,m,d);								 // ������ ��¥ ����   
				cal.add(Calendar.DATE, Library.RENT_DAYS);   // ������ ��¥ + ������� = �ݳ� ������

				Calendar cal2 = new GregorianCalendar(2018, 7, 20);		//-- �ݳ��� ���ڸ� ���� ���� ����

				// ������ �ݳ����� - �ݳ� ������(��ü�ᰡ ����� ��¥)
				long sec = (cal2.getTimeInMillis() - cal.getTimeInMillis())/ 1000;
				
				long days = sec / (24*60*60);
				
				int result = (int)days;

				int fee = result*LATE_FEE;				// ��ü�ϼ� * 300(1�� ��ü��)
				rent.setLateFee(fee);				// ��ü�� ����

				String id = rent.getRMem();			// �ݳ��� ȸ�� ���̵�
				Members mem = memList.get(id);		// �ݳ��� ȸ�� ����
				mem.setRentalBook(mem.getRentalBook() - 1);	// ���� �������� å �� -1

				Books book = bookList.get(title);
				book.setRental(true);			// �ݳ��Ǽ� ���Ⱑ������ ����
				
			}
			
			if ("������ �ð�".equals(title))		// ��ü x
			{
				rent.setReturnDate(2021, 7, 20);	// �ݳ��� ����
				rent.setReturnBook(true);			// �ݳ� o
				
				Calendar cal = Calendar.getInstance();      //-- �ݳ� �������� ���� ���� ����

				int y = rent.getRentalDate().get(Calendar.YEAR);
				int m = rent.getRentalDate().get(Calendar.MONTH);
				int d = rent.getRentalDate().get(Calendar.DATE);
				cal.set(y,m,d);								 // ������ ��¥ ����   
				cal.add(Calendar.DATE, Library.RENT_DAYS);   // ������ ��¥ + ������� = �ݳ� ������

				Calendar cal2 = new GregorianCalendar(2021, 7, 20);		//-- �ݳ��� ���ڸ� ���� ���� ����

				// ������ �ݳ����� - �ݳ� ������(��ü�ᰡ ����� ��¥)
				long sec = (cal2.getTimeInMillis() - cal.getTimeInMillis())/ 1000;
				
				long days = sec / (24*60*60);
				
				int result = (int)days;

				int fee = result*LATE_FEE;				// ��ü�ϼ� * 300(1�� ��ü��)
				rent.setLateFee(fee);				// ��ü�� ����

				String id = rent.getRMem();			// �ݳ��� ȸ�� ���̵�
				Members mem = memList.get(id);		// �ݳ��� ȸ�� ����
				mem.setRentalBook(mem.getRentalBook() - 1);	// ���� �������� å �� -1

				Books book = bookList.get(title);
				book.setRental(true);			// �ݳ��Ǽ� ���Ⱑ������ ����
				
			}

			if ("28".equals(title))		// ��ü o
			{
				
				rent.setReturnDate(2021, 7, 18);	// �ݳ��� ����
				rent.setReturnBook(true);			// �ݳ� o
				
				Calendar cal = Calendar.getInstance();      //-- �ݳ� �������� ���� ���� ����

				int y = rent.getRentalDate().get(Calendar.YEAR);
				int m = rent.getRentalDate().get(Calendar.MONTH);
				int d = rent.getRentalDate().get(Calendar.DATE);
				cal.set(y,m,d);								 // ������ ��¥ ����   
				cal.add(Calendar.DATE, Library.RENT_DAYS);   // ������ ��¥ + ������� = �ݳ� ������

				Calendar cal2 = new GregorianCalendar(2021, 7, 18);		//-- �ݳ��� ���ڸ� ���� ���� ����

				// ������ �ݳ����� - �ݳ� ������(��ü�ᰡ ����� ��¥)
				long sec = (cal2.getTimeInMillis() - cal.getTimeInMillis())/ 1000;
				
				long days = sec / (24*60*60);
				
				int result = (int)days;

				int fee = result*LATE_FEE;				// ��ü�ϼ� * 300(1�� ��ü��)
				rent.setLateFee(fee);				// ��ü�� ����

				String id = rent.getRMem();			// �ݳ��� ȸ�� ���̵�
				Members mem = memList.get(id);		// �ݳ��� ȸ�� ����
				mem.setRentalBook(mem.getRentalBook() - 1);	// ���� �������� å �� -1

				Books book = bookList.get(title);
				book.setRental(true);			// �ݳ��Ǽ� ���Ⱑ������ ����

				
			}

			
			if ("7���� ��".equals(title))		// ��ü x
			{
				//2021.7.18
				rent.setReturnDate(2021, 7, 19);	// �ݳ��� ����
				rent.setReturnBook(true);			// �ݳ� o
				
				
				rent.setLateFee(0);				// ��ü�� ����(��ü������ 0�� ����)

				String id = rent.getRMem();			// �ݳ��� ȸ�� ���̵�
				Members mem = memList.get(id);		// �ݳ��� ȸ�� ����
				mem.setRentalBook(mem.getRentalBook() - 1);	// ���� �������� å �� -1

				Books book = bookList.get(title);
				book.setRental(true);			// �ݳ��Ǽ� ���Ⱑ������ ����
				
			}
				
		}


		// rentList �׽�Ʈ �� [���� �������� å]
		rentList.add(new RentalInfo(2021, 3, 2, "���", "zxc1111"));				//��üO		����,�ݳ� �̷� O 
		rentList.add(new RentalInfo(2021, 4, 26, "������ �ູ", "zxc1111"));		//��üO		����,�ݳ� �̷� x 
		rentList.add(new RentalInfo(2021, 5, 30, "������ ����", "qwer1234"));		//��üO		����,�ݳ� �̷� O
 		rentList.add(new RentalInfo(2021, 6, 6, "����� �µ�", "qwer1234"));		//��üO		����,�ݳ� �̷� O	
		rentList.add(new RentalInfo(2021, 7, 21, "28", "sdf2222"));					//��üX		����,�ݳ� �̷� O 
		rentList.add(new RentalInfo(2021, 7, 20, "7���� ��", "sdf2222"));			//��üX		����,�ݳ� �̷� O 
		rentList.add(new RentalInfo(2021, 7, 22, "������ �ð�", "asdf5678"));		//��üX		����,�ݳ� �̷� O 

		// ���⿩�� (����)
		bookList.get("���").setRental(false);	
		memList.get("zxc1111").setRentalBook(memList.get("zxc1111").getRentalBook() + 1); 
		bookList.get("������ �ູ").setRental(false);	
		memList.get("zxc1111").setRentalBook(memList.get("zxc1111").getRentalBook() + 1); 
		bookList.get("������ ����").setRental(false);
		memList.get("qwer1234").setRentalBook(memList.get("qwer1234").getRentalBook() + 1); 
		bookList.get("����� �µ�").setRental(false);	// �������� ������ ����
		memList.get("qwer1234").setRentalBook(memList.get("qwer1234").getRentalBook() + 1); // �������� å �� +1
		bookList.get("28").setRental(false);	
		memList.get("sdf2222").setRentalBook(memList.get("sdf2222").getRentalBook() + 1); 
		bookList.get("7���� ��").setRental(false);
		memList.get("sdf2222").setRentalBook(memList.get("sdf2222").getRentalBook() + 1); 
		bookList.get("������ �ð�").setRental(false);
		memList.get("asdf5678").setRentalBook(memList.get("asdf5678").getRentalBook() + 1); 
		
		

		// wishList �׽�Ʈ ��
		wishList.add(new Wish("�̷��� ��", "������", "�Ǽ���", 2021, 7, 15));
		wishList.add(new Wish("������ ������", "��ȣ��", "������", 2021, 3, 30));
		wishList.add(new Wish("��� ������� �ұ�", "������", "����ȭ", 2019, 12, 7));
	    wishList.add(new Wish("�Ƹ��", "�տ���", "�ּ���", 2020, 10, 18));
		wishList.add(new Wish("����", "������", "�ּ���", 2020, 8, 28));
		wishList.add(new Wish("�Ÿ��� ���", "����â", "�Ǽ���", 2021, 2, 8));
		wishList.add(new Wish("���� ���� �½�", "���ʿ�", "�Ǽ���", 2020, 12, 26));
		wishList.add(new Wish("��̶�� ����", "��ҿ�", "������", 2020, 9, 9));

		// �Ž����� �׽�Ʈ��
		//-- ������� 1����, 5õ��, 1õ��, 5���, 1��� �� ����
		//    78300��
		money[0] = 1;
		money[1] = 11;
		money[2] = 2;
		money[3] = 22;
		money[4] = 3;
      
	}


	// ���θ޼ҵ�
	public static void main(String[] args) //throws IOException
	{
		try
		{
			// �׽�Ʈ �� �Է�
			inputTestData();

			// ����
			AdSystem.onSystem();
		}
		catch (Exception e)
		{
			System.out.println("\n���߸��� �� �߻���");
			System.out.println("���α׷� ���� �� �ٽ� �����ϼ���");
			System.out.println("printStackTrace.........................");
			e.printStackTrace();
		}

	}

	
}