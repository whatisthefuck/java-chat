import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerChatMain extends JFrame implements ActionListener {
    public static void main(String[] args) {
        new ServerChatMain();
    }
    //文本域
    private JTextArea jta;
    //滚动条
    private JScrollPane jsp;
    //画板
    private JPanel jp;
    //文本框
    private JTextField jtf;
    //按钮
    private JButton jb;

    private BufferedWriter bw;

    public ServerChatMain() {
        jta = new JTextArea();
        jta.setEditable(false);
        jsp = new JScrollPane(jta);
        jp = new JPanel();
        jtf = new JTextField(10);
        jb = new JButton("发送");
        jb.addActionListener(this);
        jp.add(jtf);
        jp.add(jb);
        this.add(jsp, BorderLayout.CENTER);
        this.add(jp, BorderLayout.SOUTH);
        this.setTitle("QQ聊天 服务端");
        this.setSize(300, 300);
        this.setLocation(300, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            Socket socket = serverSocket.accept();
            InputStream in = socket.getInputStream();
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println("加一行");
                jta.append(line + System.lineSeparator());
            }
            System.out.println("循环结束");
            serverSocket.close();
            System.out.println("断开连接");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = jtf.getText();
        text = "server:" + text;
        jta.append(text + System.lineSeparator());
        System.out.println(bw);
        try {
            bw.write(text);
            bw.newLine();
            bw.flush();
            jtf.setText("");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
