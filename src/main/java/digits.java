import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class digits {

    static int cnt = 0;



    public static List<puzzle> readPuzzlesFromFile(String path) throws IOException {
        System.out.println("Reading puzzles from " + path);

        BufferedReader br
                = new BufferedReader(new FileReader(path));

        String st;
        List<puzzle> puzzles= new LinkedList<puzzle>();
        while ((st = br.readLine()) != null) {
            if(st.trim().length() == 0 ) {
                continue;
            }
            puzzle p = parsePuzzle(st);
            puzzles.add(p);
        }

        return puzzles;

    }

    public static puzzle parsePuzzle(String puzzleLine) {
        String[] tokens  = puzzleLine.split(",");
        //System.out.println("Enter number of digits \n");
        int len = tokens.length;
        if(len == 0) {
            System.out.println("Enter correct data");
            System.exit(1);
        }

        float target  =   Float.parseFloat( tokens[0]);

        puzzle p = new puzzle();
        p.setTarget(target);
        int countDigits = len ;
        for(int i=1;i<countDigits;i++){

            float num = Float.parseFloat( tokens[i]);
            p.addNumber(num);
        }
        return p;
    }

    public static puzzle getDataOnCommandLine2() {
        System.out.println("Enter Puzzle \n");
        Scanner sc= new Scanner(System.in);
        String seq = sc.next();
        puzzle p = parsePuzzle(seq);
        return p;
    }

    public static OperationSequence getBestSolution(puzzle p) {
        char[] operators = {'+','-','*','%'};

        List<Float> numbers = p.getNumbers();
        List<OperationSequence> sequences = new LinkedList<OperationSequence>();

        float target = p.getTarget();
        Set<OperationSequence> targetSequence = new TreeSet<OperationSequence>();

        for(float num: numbers){

            OperationSequence seq = new OperationSequence(num);
            sequences.add((seq));

        }


        recursiveExpand(target , sequences,operators,targetSequence);
        Iterator<OperationSequence> itr = targetSequence.iterator();

        return  itr.next();

    }

    public static void main2(String s[]) {
        char[] operators = {'+','-','*','%'};
        List<OperationSequence> sequences = new LinkedList<OperationSequence>();
        puzzle p = getDataOnCommandLine2();

        OperationSequence seq = getBestSolution(p);

        seq.print();

    }

    public static void main(String s[]) throws IOException {
        char[] operators = {'+','-','*','%'};
        List<OperationSequence> sequences = new LinkedList<OperationSequence>();

        List<puzzle> puzzles = new LinkedList<puzzle>();
        try {
            puzzles = readPuzzlesFromFile("/Users/bpendse/puzzles.txt");
        } catch(IOException e) {
            System.out.println("Problem reading puzzles from file ");
            System.exit(1);
        }

        System.out.println("Puzzles read");
        long time1 = System.currentTimeMillis();
        for(puzzle p:puzzles) {
            System.out.println("Target = " + p.getTarget());
            OperationSequence seq = getBestSolution(p);
            seq.print();
            System.out.println("===============================================");
        }
        long time2 = System.currentTimeMillis();

        System.out.println("Solved all puzzles in " + ((time2-time1)/1000) + " Seconds");
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
                                if(seq.value == target) {
                                    if(!targetSequence.contains(seq)) {
                                        targetSequence.add(seq);
                                    }
                                }
                                //System.out.println(sequences.get(i).value + " " + operators[k] + " " + sequences.get(j).value + " = " + stepAnswer);
                                count = count + 1;
                                masterList.add(list);
                                recursiveExpand(target,list,operators,targetSequence);

                            }
                        }
                    }
                }
            }


        }


    }



}
