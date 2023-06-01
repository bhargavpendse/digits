import java.util.*;

public class digits {

    static int cnt = 0;

    public static  puzzle getDataOnCommandLine() {
        System.out.println("Enter target number \n");
        Scanner sc= new Scanner(System.in);
        puzzle p = new puzzle();

        float t  = sc.nextInt();
        p.setTarget(t);
        //System.out.println("Enter number of digits \n");
        int countDigits = 6;//sc.nextInt();
        for(int i=0;i<countDigits;i++){
            System.out.println("Enter digit");
            float num = sc.nextFloat();
           // OperationSequence seq = new OperationSequence(num);
           // sequences.add((seq));
            p.addNumber(num);
        }
        return p;
    }

    public static puzzle getDataOnCommandLine2() {
        System.out.println("Enter Puzzle \n");
        Scanner sc= new Scanner(System.in);
        String seq = sc.next();
        String[] tokens  = seq.split(",");
        //System.out.println("Enter number of digits \n");
        int len = tokens.length;
        if(len == 0) {
            System.out.println("Enter correct data");
            System.exit(1);
        }

        float target  =   Float.parseFloat( tokens[0]);

        puzzle p = new puzzle();
        p.setTarget(target);
        int countDigits = len -1;
        for(int i=1;i<countDigits;i++){

            float num = Float.parseFloat( tokens[i]);
            p.addNumber(num);
        }

        return p;

    }


    public static void main(String s[]) {
        char[] operators = {'+','-','*','%'};
        List<OperationSequence> sequences = new LinkedList<OperationSequence>();
        puzzle p = getDataOnCommandLine2();
        List<Float> numbers = p.getNumbers();

        float target = p.getTarget();
        System.out.println("Target = " + target);

        Set<OperationSequence> targetSequence = new TreeSet<OperationSequence>();

        for(float num: numbers){

             OperationSequence seq = new OperationSequence(num);
             sequences.add((seq));

        }
        recursiveExpand(target , sequences,operators,targetSequence);

        System.out.println("Done .. found " + cnt + " solutions");
        System.out.println("Top " +  targetSequence.size() + " equations using provided digits to get " + target);
        int i=0;


        Iterator<OperationSequence> itr = targetSequence.iterator();
        while (itr.hasNext())
        {
            itr.next().print();
        }

    }

    public static void printList2(float target , List<OperationSequence> list,Set<OperationSequence> targetSequence) {
        for(OperationSequence seq:list){
            if(seq.value == target) {
                cnt++;
                if(!targetSequence.contains(seq)) {

                    targetSequence.add(seq);
                }
            }
        }
    }
    public static void printList(float target , List<List<OperationSequence>> sequences,Set<OperationSequence> targetSequence) {
        for(List<OperationSequence> list:sequences){
           printList2(target,list,targetSequence);
        }

    }



    public static void recursiveExpand(float target , List<OperationSequence> sequences,char[] operators,Set<OperationSequence> targetSequence) {
        int count = 0;
        List<List<OperationSequence>>  masterList = new LinkedList<List<OperationSequence>>();
        for(int i=0;i<sequences.size();i++) {
            for(int j=0;j<sequences.size()  ;j++) {
                for(int k=0;k<operators.length ;k++) {
                    {
                        float stepAnswer = 0;
                        switch(operators[k]) {
                            case '+': {
                                stepAnswer = sequences.get(i).value + sequences.get(j).value;
                                break;
                            }
                            case '-': {
                                stepAnswer = sequences.get(i).value - sequences.get(j).value;
                                break;
                            }
                            case '*': {
                                stepAnswer = sequences.get(i).value * sequences.get(j).value;
                                break;
                            }
                            case '%': {
                                stepAnswer = sequences.get(i).value / sequences.get(j).value;
                                break;
                            }

                        }

                        if(i != j) {
                            List<OperationSequence> list = new LinkedList<OperationSequence>();
                            for(int z=0;z<sequences.size();z++) {
                                if(z != i && z!=j) {
                                    list.add(sequences.get(z));
                                }
                            }

                            OperationSequence seq = sequences.get(i).append(sequences.get(j),operators[k]);
                            if(seq.value > 0  && seq.value == Math.ceil(seq.value)) {
                                list.add(seq);
                                //System.out.println(sequences.get(i).value + " " + operators[k] + " " + sequences.get(j).value + " = " + stepAnswer);
                                count = count + 1;
                                masterList.add(list);
                            }
                        }
                    }
                }
            }

            printList(target ,masterList,targetSequence);
            for(List<OperationSequence> list : masterList) {
                if(list.size() != 1) {
                    recursiveExpand(target,list,operators,targetSequence);
                }
            }
        }


    }



}
