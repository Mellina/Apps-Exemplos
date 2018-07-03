package br.com.softblue.android.http;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Resposta do servidor a uma requisição HTTP
 */
public class HttpResponse implements Closeable {

    /**
     * Código HTTP da resposta
     */
    private int responseCode;

    /**
     * Mensagem da resposta
     */
    private String responseMessage;

    /**
     * InputStream com os dados a serem lidos
     */
    private InputStream inputStream;

    public HttpResponse(int responseCode, String responseMessage, InputStream inputStream) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.inputStream = inputStream;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * Lê os dados da InputStream como texto para dentro de uma String
     * @return Retorna a String resultante
     * @throws IOException Erro de I/O
     */
    public String extractDataAsString() throws IOException {
        return extractDataAsString(Charset.defaultCharset().name());
    }
    /**
     * Lê os dados da InputStream como texto para dentro de uma String
     * @param charset Charset utilizado na leitura dos dados
     * @return Dados da stream em uma String
     * @throws IOException Erro de I/O
     */
    public String extractDataAsString(String charset) throws IOException {
        StringBuilder content = new StringBuilder();
        String line;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charset))) {
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }

        return content.toString();
    }

    /**
     * Fecha a InputStream
     * @throws IOException Erro de I/O
     */
    @Override
    public void close() throws IOException {
        if (inputStream != null) {
            inputStream.close();
        }
    }
}
