import java.util.Calendar;
import java.util.GregorianCalendar;


class Members					// ȸ�� VO Ŭ����
{
	private String pw;					// ��й�ȣ
	private String name;				// �̸�
	private String ssn;					// �ֹε�Ϲ�ȣ  
	private int rentalBook;				// ���� ���� å ��

	Members(String p, String n, String s)
	{
		pw = p;
		name = n;
		ssn = s;

		rentalBook = 0;		// �⺻�� : ���� 0��
	}

	//getter
	public String getPw() {
		return pw;
	}
	public String getName() {
		return name;
	}
	public String getSsn() {
		return ssn;
	}
	public int getRentalBook() {
		return rentalBook;
	}
	

	//setter
	public void setRentalBook(int r) {
		rentalBook = r;
	}	
}


class Books						// å VO Ŭ����
{
	private String author;				// ���ڸ�
	private String publisher;			// ���ǻ�
	private String pubYear;        		// ���ǳ⵵
	private String codeNumber;  		// �з���ȣ
	private String category;          	// ī�װ�
	private Calendar storedDate;		// ������ �԰�����
	private boolean rental;	 			// ���� ���� ���� (true : ���� ���� / false : ���� ��)	


	// ������
	Books(String a, String p, String y, String cN, String ca, int year, int month, int day)
	{
		author		= a;
		publisher	= p;
		pubYear		= y;
		codeNumber	= cN;
		category	= ca;
		
		storedDate = new GregorianCalendar(year, month, day);
		rental = true;			// �⺻��: ���� ����(true)
	}


	// getter
	public boolean isRental() {
		return rental;
	}
	
	public String getAuthor() {
		return author;
	}
	public String getPublisher() {
		return publisher;
	}
	public String getPubYear() {
		return pubYear;
	}
	public String getCodeNumber() {
		return codeNumber;
	}
	public String getCategory() {
		return category;
	}
	public Calendar getStoredDate() {
		return storedDate;	
	}

	// �߰� getter �޼ҵ�
	String getStoredDateStr()		
	{
		String result = storedDate.get(Calendar.YEAR) + "/" 
						+ (storedDate.get(Calendar.MONTH) + 1) + "/"
						+ storedDate.get(Calendar.DATE);			// ��� ��) 2019/2/7
		return result;
	}

	String isRentalStr()
	{
		if (rental)
			return "Y";	// ���� ����
		else 
			return "N"; // ���� �Ұ���
	}

	// setter
	public void setRental(boolean r) {
		rental = r;		// ���� ���� ���� (true : ���� ���� / false : ���� ��)	
	}
}


class RentalInfo
{
	private Calendar rentalDate;				// ������ ����
	private Calendar returnDate;				// �ݳ��� ����
	private String rBook;						// ���⵵��
	private String rMem;						// ����ȸ��
	private int lateFee;						// ��ü��
	private boolean returnBook;					// �ݳ����� ����(true: �ݳ��Ϸ�, false: ���� ��) 

	// ������
	RentalInfo(int rentalY, int rentalM, int rentalD, String book, String mem)
	{
		rentalDate = new GregorianCalendar(rentalY, rentalM, rentalD);
		rBook = book;
		rMem = mem;

		lateFee = 0;			// �⺻�� : ��ü�� 0��
		returnBook = false;		// �⺻�� : �ݳ��Ϸ�
	}

	// getter
	public String getRBook() {
		return rBook;
	}
	public String getRMem() {
		return rMem;
	}
	public int getLateFee() {
		return lateFee;
	}
	public boolean isReturnBook()
	{
		return returnBook;
	}
	public Calendar getRentalDate() 
	{
		return rentalDate;
	}


	// �߰� getter �޼ҵ�
	String getRentalDateStr()		
	{
		String result = rentalDate.get(Calendar.YEAR) + "/" 
						+ (rentalDate.get(Calendar.MONTH)+1) + "/"
						+ rentalDate.get(Calendar.DATE);			// ��� ��) 2019/2/7
		return result;
	}

	String getReturnDateStr()		
	{
		String result = returnDate.get(Calendar.YEAR) + "/" 
						+ (returnDate.get(Calendar.MONTH)+1) + "/"
						+ returnDate.get(Calendar.DATE);
		return result;
	}

	// setter
	public void setReturnDate(int returnY, int returnM, int returnD) {
		this.returnDate	= new GregorianCalendar(returnY, returnM, returnD);
	
	}
	public void setLateFee(int f) {
		lateFee = f;
	}
	public void setReturnBook(boolean r) {
		returnBook = r;
	}

}

class Wish
{
   private String wTitle;		//������
   private String wAuthor;		//���ڸ�
   private String wMem;         //��û�ڸ�
   private Calendar reqDate;    //��û����
   private int request;         //��û����(1:��û�Ϸ�, 2:�԰�Ϸ�, 3:��û�ź�)

   // ������
   Wish(String wT, String wA, String wM, int year, int month, int day)
   {
      this.wTitle  = wT;
      this.wAuthor = wA;
      this.wMem = wM;
      this.reqDate = new GregorianCalendar(year, month, day);

      this.request = 1;   // �⺻�� : ��û�Ϸ�
   }

   // getter
   public int getRequest() {
      return request;
   }
   public String getWTitle() {
      return wTitle;
   }
   public String getWAuthor() {
      return wAuthor;
   }
   public String getWMem() {
      return wMem;
   }
   public Calendar getReqDate() {
      return reqDate;   
   }

   // �߰� getter �޼ҵ�
   String getReqDateStr()      
   {
      String result = reqDate.get(Calendar.YEAR) + "/" 
                  + ( reqDate.get(Calendar.MONTH)+1 ) + "/"
                  + reqDate.get(Calendar.DATE);         // ��� ��) 2019/2/7
      return result;
   }

	String getRequestStr()
	{
		switch (request)
		{
			case 1: return "��û�Ϸ�";         
			case 2: return "�԰�Ϸ�";        
			case 3: return "��û�ź�";
			default: return "-";
		}
	}

   // setter
   public void setRequest(int re) {
      request = re;
   }
}