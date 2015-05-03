/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Communication;

import java.awt.Color;
import java.io.DataOutputStream;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;

/**
 *
 * @author Kunal
 */
public class Speaker extends SwingWorker implements Runnable{

    @Override
    protected Object doInBackground() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
public class UserVerifier extends SwingWorker<Boolean, Boolean> {
        @Override
        protected Boolean doInBackground() throws Exception {
        return null;

        }

        @Override
        protected void done() {
        }
    }
}