package pl.edu.agh.kis.messages;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Karl on 28.01.2017.
 */
public class MessageAbstractTest {
    @Mock
    Socket socket;

    @Test
    public void sendingMessageWithOneByte() {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final byte mes[] = {1};
        MessageAbstract messageAbstract = null;
        messageAbstract = new MessageAbstract(byteArrayOutputStream) {
            @Override
            public void send() {
                try {
                    out.write(mes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            public OutputStream getOut() {
                return byteArrayOutputStream;
            }
        };
        messageAbstract.send();
        Assert.assertEquals(mes[0], byteArrayOutputStream.toByteArray()[0]);


    }

    @Test
    public void sendingMessageWithCoupleOfBytes() {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final byte mes[] = {1, 2, 5, 1, 2, 4, 6};
        MessageAbstract messageAbstract = null;
        messageAbstract = new MessageAbstract(byteArrayOutputStream) {
            @Override
            public void send() {
                try {
                    for (int i = 0; i < mes.length; i++) {
                        out.write(mes[i]);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            public OutputStream getOut() {
                return byteArrayOutputStream;
            }
        };
        messageAbstract.send();
        for (int i = 0; i < mes.length; i++) {
            Assert.assertEquals(mes[i], byteArrayOutputStream.toByteArray()[i]);
        }
    }

    @Test
    public void sendNullTest() {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        MessageAbstract messageAbstract = null;
        messageAbstract = new MessageAbstract(byteArrayOutputStream) {
            @Override
            public void send() {
            }

            public OutputStream getOut() {
                return byteArrayOutputStream;
            }
        };
        messageAbstract.send();
        Assert.assertEquals(0, byteArrayOutputStream.size());
    }

    @Test
    public void sendingStringTest() {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final byte[] mes = "textToSend".getBytes();
        MessageAbstract messageAbstract = null;
        messageAbstract = new MessageAbstract(byteArrayOutputStream) {
            @Override
            public void send() {
                try {
                    out.write(mes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        messageAbstract.send();
        for (int i = 0; i < mes.length; i++) {
            Assert.assertEquals(mes[i], byteArrayOutputStream.toByteArray()[i]);
        }


    }


}