public class OperationSequence implements java.lang.Comparable{
    String StrSequence = "";
    float value;
    int numOperations = 0;

    public OperationSequence(float f){
        StrSequence = "(" + f + ")";
        value = f;
    }

    public OperationSequence(float f,String seq,int numOps){
        StrSequence = seq;
        value = f;
        numOperations = numOps;
    }

    public OperationSequence append(OperationSequence seq,char operator){
        float f = seq.value;
        float newValue = 0;


        String strSeq = "(" + StrSequence + " " + operator + " " + seq.StrSequence + ")";
        switch(operator) {
            case '+': {
                newValue = value + f;
                break;
            }
            case '-': {
                newValue = value - f;
                break;
            }
            case '*': {
                newValue = value * f;
                break;
            }
            case '%': {
                newValue = value / f;
                break;
            }

        }
        int numOps = seq.numOperations + numOperations;
        if(numOps == 0) {
            numOps = 1;
        }
        return new OperationSequence(newValue,strSeq,numOps);
    }



    public void print(){
        System.out.println(this.StrSequence + " VALUE = " + this.value);
    }


    public int getNumOperations() {
        int count = 0;

        char[] chars = {'+','-','*', '%'};
        for(char c:chars) {
            for (int i = 0; i < StrSequence.length(); i++) {
                if (StrSequence.charAt(i) == c) {
                    count++;
                }
            }
        }

        return count;
    }


    @Override
    public int compareTo(Object o) {

        int count1 = 0;
        int count2 = 0;

        count1 = getNumOperations();

        OperationSequence obj = (OperationSequence)o;
        count2 = obj.getNumOperations();

        return count1-count2;
    }
}
