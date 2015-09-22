using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Server
{   
    //Our server's main
    class Program
    {
        static void Main(string[] args)
        {
            Server server = new Server();
        }       
    }


    class Server{
        static Socket listeningSocket;
    static Socket socket;
    static Thread thrReadRequest;
    static int iPort = 4444;
    static int iConnectionQueue = 100;

    protected bool listen = true;

    public Server()
    {
        Console.WriteLine(IPAddress.Parse(getLocalIPAddress()).ToString());
        try
        {
            listeningSocket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

            listeningSocket.Bind(new IPEndPoint(IPAddress.Parse(getLocalIPAddress()), iPort));
            listeningSocket.Listen(iConnectionQueue);

            thrReadRequest = new Thread(new ThreadStart(getRequest));
            thrReadRequest.Start();
        }
        catch (Exception e)
        {
            Console.WriteLine("Winsock error: " + e.ToString());
            //throw;
        }
    }

    private void getRequest()
    {
        socket = listeningSocket.Accept();
        Console.WriteLine("Connected...");
        while (listen)
        {
            try
            {

                // Receiving

                //byte[] buffer = new byte[socket.SendBufferSize];
                //int iBufferLength = socket.Receive(buffer, 0, buffer.Length, 0);

                //Console.WriteLine("Received {0}", iBufferLength);
                //Array.Resize(ref buffer, iBufferLength);
                //string formattedBuffer = Encoding.ASCII.GetString(buffer);

                // Receiving
                byte[] rcvLenBytes = new byte[4];
                socket.Receive(rcvLenBytes);
                int rcvLen = BitConverter.ToInt32(rcvLenBytes, 0);
                byte[] rcvBytes = new byte[rcvLen];
                socket.Receive(rcvBytes);
                String formattedBuffer = System.Text.Encoding.ASCII.GetString(rcvBytes);

                Console.WriteLine("Android Says: {0}", formattedBuffer);


                socket.Close();
                listeningSocket.Close();
                Console.WriteLine("Exiting");
                listen = false;
                //Environment.Exit(0);


                //Console.WriteLine("Inside Try i = {0}", i.ToString());
                Thread.Sleep(5000);
            }
            catch (Exception e)
            {
                //socket.Close();
                Console.WriteLine("Receiving error: " + e.ToString());
                Console.ReadKey();
                //throw;
            }
            finally
            {
                socket.Close();
                //listeningsocket.close();
            }
        }
    }

    //Returns our host IP
    static private string getLocalIPAddress()
    {
        IPHostEntry host;
        string localIP = "";
        host = Dns.GetHostEntry(Dns.GetHostName());
        foreach (IPAddress ip in host.AddressList)
        {
            if (ip.AddressFamily == AddressFamily.InterNetwork)
            {
                localIP = ip.ToString();
                break;
            }
        }
        return localIP;
    }
}
}
