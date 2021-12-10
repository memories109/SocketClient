package com.test;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class TestClient {
	
	private String header = "0400";
	private String crtcrdSlsTpCd = "1";
	private String crtcrdlnType = "01";
	private String [] ci = {String.format("%-100s", "7SsxUpQiT8X3Y4VUQPI2lOAfqBttu2jkS+BsGSngqqyysdmOlZNEJ9Vkh9SlfQca9ULTQC8pCLFnSxBdPfFNHA==") // Q150000683014, 이*배
							, String.format("%-100s", "2JGfSzi31aI7yyhttk2gJHJXx15U0+korcxR8k602OaLX/DwwJK9ZWDpngOGsTestsmIi2XRIgSunIdlsuXY5vyQ==") // Q150001970903, 정*홍
							, String.format("%-100s", "K0BRVcEOueS+wyJg4P7z2NIz0YG2B4QrJ5Ke+Qg5JUtoXBhjRh8LljTmtJGgXcIHeXxRxz1A0dTOXJggQazVIg==") }; // Q150001970957, 손*식
	private String crtcrdNo = "1020";
	private String instCrdcrdTpCd = "1";
	private String crtcrdApprNo = String.format("%-10s", "00456561");
	private String usePrc = null;		//	"00000005500" 11자리
	private String mcnsNm = String.format("%-100s", "TEST MCNS NAME");
	private String famCrtcrdTpCd = "1";
	private String mcnsBntpNo = String.format("%-5s", "5107");
	private String mcnsBntpNm = String.format("%-30s", "TEST BNTP NAME");
	private String mcnsBizno = "4180750850";
	private String crtcrdUseDt = "20190409102440";										// 사용일
	private String crtcrdProdNo = String.format("%-5s", "00436");
	private String rtAcptPk = null;  // "4137009EACB494D4FF8092A43CAB5ACCB7DA032018122000456561      "; 60자리
	private String slsPartAcpt= "0"; 
	private String mcnsNo = String.format("%-10s", "560587024");
	private String crdUseTpCd = "N";
	private String filler = "                                  ";
	
	private String [] ip = {"127.0.0.1", "192.168.91.21"};			// 0:local , 1:card server
	private int port = 7405;				// local 및 dev 포트
	
    public static void main(String[] args) {
    	TestClient client = new TestClient();
    	client.start();
    }
    
    public void start() {
    	int count;
    	int ciCount;
    	
    	Socket socket = new Socket();
    	String command = null;
		String targetIP = null;
	
    	try {
    		InetSocketAddress ipep = null;
    		Scanner sc = new Scanner(System.in);
    		
    		System.out.println("테스트 전에 dev 유저의 휴대폰 번호가 00000000 으로 리셋되어 있는지 반드시 확인 할 것" );
    		System.out.println("1 : local, 2 : card server (기본 local)");
    		System.out.print("> ");
    		String mode = sc.nextLine();

    		targetIP =  mode.equals("2") ? ip[1] : ip[0];
    		ipep = new InetSocketAddress(targetIP, port) ;
    		
    		socket.connect(ipep);
    		
    		System.out.println("socket connected..");   			
    		OutputStream os = socket.getOutputStream();
    		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "MS949"));
    		


    		while (true) {
    			
    			System.out.println("\n 전송 IP : "+targetIP);
    			System.out.println("----------------------------");
    			System.out.println("S : 테스트 전송 ");
    			System.out.println("Q : 종료 ");
    			System.out.print("> ");
    			command = sc.nextLine();
    			
    			if ( socket.isConnected() && socket.isClosed() ) {		// 서버쪽에서 끊어 졌으면 재연결
    				socket.connect(ipep);
    	    		
    	    		System.out.println("socket re-connected..");   			
    	    		os = socket.getOutputStream();
    	    		bw = new BufferedWriter(new OutputStreamWriter(os, "MS949"));    				
    			}    			
    			
    			if (command.equalsIgnoreCase("S")) {

	    			System.out.print("전송할 CI 수 (기본 1) : ");
	    			try {
	    				ciCount = Integer.valueOf(sc.nextLine());
	    			} catch (NumberFormatException Ex) {
	    				ciCount = 1;		// 입력 실수는 1건으로.
	    			}
	    			
    				System.out.print("CI당 전송 건 수 (기본 1) : ");
	    			try {
	    				count = Integer.valueOf(sc.nextLine());
	    			} catch (NumberFormatException Ex) {
	    				count = 1;		// 입력 실수는 1건으로.
	    			}
	    			
	    			System.out.print("사용금액 (기본 10): ");
	    			int temp;
	    			try {
	    				temp = Integer.valueOf(sc.nextLine());
	    			} catch (NumberFormatException Ex) {
	    				temp = 10;		// 입력 실수는 10 원
	    			}
	    			//usePrc = String.format("%011d", temp);
	    			

	    			/*Socket client = new Socket();
	    	    	InetSocketAddress ipep = new InetSocketAddress("127.0.0.1", 7405);
	    	    	client.connect(ipep);
	        		
	        		System.out.println("socket connected..");   			
	        		OutputStream os = client.getOutputStream();
	        		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "MS949"));*/	    	    	
	    			
	    			String msg = null;
	    			for ( int j =0; j< ciCount; j++) { 
		    			for ( int i =0 ; i  < count ; i++ ) {			// 지정된 횟수 만큼 서버로 전송
		    				String curDate = DateUtil.getCurrentDateTimeString();
		    				rtAcptPk = "test "+curDate +" - "+ j +" - "+i;
		    				
		    				rtAcptPk = String.format("%-60s", rtAcptPk);		// 60자리 채움
		    				
		    				usePrc = String.format("%011d", temp + (i*10));
		    				
		    				msg = header + crtcrdSlsTpCd + crtcrdlnType + ci[j] + crtcrdNo + instCrdcrdTpCd 
		    						+ crtcrdApprNo + usePrc + mcnsNm + famCrtcrdTpCd + mcnsBntpNo 
		    						+ mcnsBntpNm + mcnsBizno + crtcrdUseDt + crtcrdProdNo + rtAcptPk 
		    						+ slsPartAcpt + mcnsNo + crdUseTpCd + filler;
		    				
		    				System.out.println("Transfering msg ... "+msg);
		    				
		    				bw.write(msg);
		    			}
	    			}
	    			
	    			bw.flush();
	    			
	    			//bw.close();
	        		//client.close();
	        		//System.out.println("socket closed..");
		    			
    			} else if(command.equalsIgnoreCase("Q")) {
    				break; 
    			}
				
    		}
    		
    		bw.close();
    		socket.close();
    		
    		System.out.println("socket closed..");    		
			sc.close();
			
    	} catch (Exception ex ) {
    		ex.printStackTrace();
    	}
    	
    	
    }
}
