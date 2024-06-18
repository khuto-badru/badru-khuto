/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package asd;

import static com.sun.org.apache.xalan.internal.lib.NodeInfo.lineNumber;

/**
 *
 * @author Hamza
 */
public class TokenWithLine {

   private String token;
    private int lineNumber;

    public TokenWithLine(String token, int lineNumber) {
        this.token = token;
        this.lineNumber = lineNumber;
        
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

}
