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

	// ���� �ִ� �� ����
	public static int moneyTot(int[] m)
	{   
		return (10000*m[0]) + (5000*m[1]) + (1000*m[2]) + (500*m[3]) + (100*m[4]);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////


	// ��ü ���� Ȯ�Ψ� : �Է� ���� ������Ȳ�� ���� ��ü ����
	public static String overdue(RentalInfo r)            
	{         
		if (calLateDays(r) == 0)
			return "N";
		else
			return "Y";
	}


	// ��ü ���� Ȯ�Ψ� : �Է� ���� �̿��ڿ� ���� ��ü ����  ��isOverdueStr ��ü
	public static String overdue(String id) // throws IOException             
	{   
		String result = "-";
		for(int i=0; i<Library.rentList.size(); i++)
		{
			RentalInfo valR = Library.rentList.get(i);
			if((valR.getRMem()).equals(id))
			{
				if(!(valR.isReturnBook()) && calLateFee(valR)>0)   //�ݳ�X, ��ü�ᰡ ������ ���� ��ü��
					result = "Y";
				else
					result = "N";
			}
		}
		return result;
	}


	// ��ü�� ���� (�Ű����� ��������) : �Է� ���� ������Ȳ�� ���� �̳� ��ü��
	public static int calLateFee(RentalInfo r)         
	{
		return calLateDays(r) * Library.LATE_FEE;
	}


	// ��ü�� ���� (�Ű����� id) : �Է� ���� �̿����� �� �̳� ��ü��
	public static int calLateFee(String id)
	{
		int result = 0; //-- �̳� ��ü�� �������� ���� ���� ����

		// �� ���� ��Ȳ ��Ͽ��� �Է� ���� �̿���(id)�� ���� ��Ȳ�� ã�Ƴ���,
		// �� �̳��� ���
		// �� ������ ���ϱ�
		for(int i=0; i<Library.rentList.size(); i++)
		{
			RentalInfo valR = Library.rentList.get(i);

			if((valR.getRMem()).equals(id) && !valR.isReturnBook())   // �� && ��
			result += calLateFee(valR);   // ��
		}

		return result;
	}

	// ��ü�� ��� �� (�Ű����� id, f) : �Է� ���� �̿����� �� ��ü��(����/�̳�)
	public static int calLateFee(String id, boolean f)
	{
		// �ݳ����� ����(true: �ݳ��Ϸ�, false: ���� ��) 
		// f = true  �� ��� �� ���� ��ü�� ��ȯ
		//     false �� ��� �� �̳� ��ü�� ��ȯ

		int result1 = 0; //-- ���� ��ü�� �������� ���� ���� ����
		int result2 = 0; //-- �̳� ��ü�� �������� ���� ���� ����

		// �� ���� ��Ȳ ��Ͽ��� �Է� ���� �̿���(id)�� ���� ��Ȳ�� ã�Ƴ���,
		// �� �ݳ� / �̳��� ���
		// �� ������ ���ϱ�
		for(int i=0; i<Library.rentList.size(); i++)
		{
			RentalInfo valR = Library.rentList.get(i);

			if((valR.getRMem()).equals(id) && valR.isReturnBook())         // �� && �� : �ݳ� O
				result1 += valR.getLateFee();   // ��
			else if ((valR.getRMem()).equals(id) && !valR.isReturnBook())   // �� && �� : �ݳ� X
				result2 += calLateFee(valR);   // ��
		}

		if (f)
			return result1;
		else
			return result2;
	}

	// ��ü�� ��� �� : �Է� ���� �̿��� Ȥ�� ������ �� ��ü��(����/�̳�)
	// -->String, boolean ���� �޴� �޼ҵ尡 ������ �ֱ� ������(�̿��� ���� �޼ҵ�) �����ε��� �� ��.
	//    ���� ���� ���θ� �Ǵ��ϴ� boolean ���� �߰��� ����
	//     (true: �̿����� ��ü��, false: ������ ��ü��)
	public static int calLateFee(String k, boolean f, boolean b)
	{
		// �ݳ����� ����(true: �ݳ��Ϸ�, false: ���� ��) 
		// f = true  �� ��� �� ���� ��ü�� ��ȯ
		//     false �� ��� �� �̳� ��ü�� ��ȯ

		int result1 = 0; //-- ���� ��ü�� �������� ���� ���� ����
		int result2 = 0; //-- �̳� ��ü�� �������� ���� ���� ����

		// �� ���� ��Ȳ ��Ͽ��� �Է� ���� �̿���(id) Ȥ�� ����(title)�� ���� ��Ȳ�� ã�Ƴ���,
		// �� �ݳ� / �̳��� ���
		// �� ������ ���ϱ�

		if (b)
		{
			for(int i=0; i<Library.rentList.size(); i++)
			{
				RentalInfo valR = Library.rentList.get(i);

				if((valR.getRMem()).equals(k) && valR.isReturnBook())         // �� && �� : �ݳ� O
					result1 += valR.getLateFee();   // ��
				else if ((valR.getRMem()).equals(k) && !valR.isReturnBook())   // �� && �� : �ݳ� X
					result2 += calLateFee(valR);   // ��
			}
		}
		else
		{         
			for(int i=0; i<Library.rentList.size(); i++)
			{
				RentalInfo valR = Library.rentList.get(i);

				if((valR.getRBook()).equals(k) && valR.isReturnBook())         // �� && �� : �ݳ� O
					result1 += valR.getLateFee();   // ��
				else if ((valR.getRBook()).equals(k) && !valR.isReturnBook())   // �� && �� : �ݳ� X
					result2 += calLateFee(valR);   // ��
			}
		}
      
		if (f)
			return result1;
		else
			return result2;
	}


	// ��ü�ϼ� ���
	public static int calLateDays(RentalInfo r)         
	{
		int result=0; 
		//������ ��¥+14��(Library.RENT_DAYS) ~ �κ��� ����(�� �޼ҵ带 �ҷ����� ����)���� ������ ��������..

		// �� �ݳ� ������ ���                  
		Calendar cal = new GregorianCalendar();       //-- �ݳ� �������� ���� ���� ����

		int y = r.getRentalDate().get(Calendar.YEAR);
		int m = r.getRentalDate().get(Calendar.MONTH);
		int d = r.getRentalDate().get(Calendar.DATE);
		cal.set(y,m,d);                        // ������ ��¥ ����   
		cal.add(Calendar.DATE, Library.RENT_DAYS);   // ������ ��¥ + ������� = �ݳ� ������

		// �� �ݳ� ������ ~ ���ñ��� �� �� ���
		Calendar today = new GregorianCalendar();   //-- ����         

		long tempR = today.getTimeInMillis() - cal.getTimeInMillis();			//���� - �ݳ�������
		if ( tempR<=0 )            //   �ݳ��������� ���� ���� ���� (�̷�)      
			result = 0;
		else if (tempR>0)          //   �ݳ��������� ���� (����)   
		{
			long sec = (today.getTimeInMillis() - cal.getTimeInMillis())/ 1000;   // '���� - �ݳ�������(����)' �� ��

			// �԰�¥�� ���糯¥�� ���̸� �� ������ ��ȯ
			// �� 1�� = 24(�ð�) * 60(��) * 60(��)
			//     ����, sec ������ 24*60*60 �� ���� ������ �� ���� �Ϸ� ��ȯ ����
			long days = sec / (24*60*60);

			result = (int)days;
		}

		return result;
	}


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	// �Ű�������� ���
	public static void newBookListPrint()             
	{
		Enumeration titles = Library.bookList.keys();   // Ű�� ������
		Calendar today = new GregorianCalendar();      // ���� ��¥ cal�� ���
                  
      
		System.out.println("\n[�ű� �԰� ���� ���]");
		System.out.println("===========================================================================================================");
		System.out.printf("������ \t���ڸ� \t���ǻ� \t���ǳ⵵ \t�з���ȣ \tī�װ� \t�԰����� \t���⿩��\n");
		System.out.println("===========================================================================================================");
         
		while (titles.hasMoreElements()) // Ű�� �����ϴ� �� ����
		{
			String title = String.valueOf(titles.nextElement());   // ������° Ű�� �Ѿ
         
			// �԰�¥�� ���糯¥�� ���̸� �� ������ ��ȯ (õ���� 1�� ������ �޾ƿ� �� 1000���� ����)
			long sec = (today.getTimeInMillis() - Library.bookList.get(title).getStoredDate().getTimeInMillis()) / 1000;
            
			// �԰�¥�� ���糯¥�� ���̸� �� ������ ��ȯ
			// �� 1�� = 24(�ð�) * 60(��) * 60(��)
			//     ����, sec ������ 24*60*60 �� ���� ������ �� ���� �Ϸ� ��ȯ ����
			long days = sec / (24*60*60);
       
			// �԰�¥�� ���糯¥�� ���̰� 31�� �̸��� �� ���
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