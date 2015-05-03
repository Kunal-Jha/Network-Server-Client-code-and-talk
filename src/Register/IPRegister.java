
package Register;

/**
 *
 * @author Kunal
 */
public class IPRegister {
    private static String [] IPArray = new String[10];
    private static int curLoc = -1;
    public static void registerIP(String ip){
        int IPArrayLen = IPArray.length;
        IPArray[++curLoc] = ip;
    }

    public static int removeIP(String ip){
        int removed = -1;
        int IPArrayLength = IPArray.length;
        String[] tempIPArray = IPArray;
        for(int i = 0; i < IPArrayLength; i++){
            if(ip.equals(tempIPArray[i])){
                removed = i;
                IPArray[i] = null;
                break;
            }
        }
        return removed;
    }

    public static boolean isRegistered(String ip){
        int IPArrayLength = IPArray.length;
        String[] tempIPArray = IPArray;
        boolean found = false;
        for(int i = 0; i < IPArrayLength; i++){
            if(ip.equals(tempIPArray[i])){
                found = true;
                break;
            }
        }
        return found;
    
    }
}