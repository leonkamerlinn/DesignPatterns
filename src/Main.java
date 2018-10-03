import behavioral.chain_responsibility.ATMDispenseChain;
import behavioral.chain_responsibility.Currency;
import behavioral.command.*;
import behavioral.intepreter.InterpreterClient;
import behavioral.intepreter.InterpreterContext;
import behavioral.iterator.*;
import behavioral.mediator.ChatMediator;
import behavioral.mediator.ChatMediatorImpl;
import behavioral.mediator.User;
import behavioral.mediator.UserImpl;
import behavioral.memento.FileWriterCaretaker;
import behavioral.memento.FileWriterUtil;
import behavioral.observer.MyTopic;
import behavioral.observer.MyTopicSubscriber;
import behavioral.observer.Observer;
import behavioral.state.State;
import behavioral.state.TVContext;
import behavioral.state.TVStartState;
import behavioral.state.TVStopState;
import behavioral.strategy.CreditCardStrategy;
import behavioral.strategy.Item;
import behavioral.strategy.PaypalStrategy;
import behavioral.strategy.ShoppingCart;
import behavioral.template_method.GlassHouse;
import behavioral.template_method.HouseTemplate;
import behavioral.template_method.WoodenHouse;
import behavioral.visitor.*;
import creational.abstract_factory.pc.PCFactory;
import creational.abstract_factory.server.ServerFactory;
import creational.factory.Computer;
import creational.factory.ComputerFactory;
import creational.prototype.Employees;
import creational.singleton.eager.EagerInitializedSingleton;
import structural.adapter.SocketAdapter;
import structural.adapter.SocketClassAdapterImpl;
import structural.adapter.SocketObjectAdapterImpl;
import structural.adapter.Volt;
import structural.bridge.GreenColor;
import structural.bridge.Pentagon;
import structural.bridge.RedColor;
import structural.composite.Circle;
import structural.composite.Drawing;
import structural.composite.Shape;
import structural.composite.Triangle;
import structural.decorator.BasicCar;
import structural.decorator.Car;
import structural.decorator.LuxuryCar;
import structural.decorator.SportsCar;
import structural.facade.HelperFacade;
import structural.facade.MySqlHelper;
import structural.facade.OracleHelper;
import structural.proxy.CommandExecutor;
import structural.proxy.CommandExecutorProxy;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        TestFactory();
        ReflectionSingletonTest();
        TestAbstractFactory();
        TestClassAdapter();
        TestObjectAdapter();
        ProxyTest();
        FacadeTest();
        BridgeTest();
        DecoratorTest();
        TemplateMethodTest();
        MediatorTest();
        ChainResponsibilityTest();
        ObserverTest();
        StrategyTest();
        CommandTest();
        StateTest();
        VisitorTest();
        InterpreterTest();
        IteratorTest();
        MementoTest();
    }

    private static void TestFactory() {
        Computer pc = ComputerFactory.getComputer("pc", "2 GB", "500 GB", "2.4 GHz");
        Computer server = ComputerFactory.getComputer("server", "16 GB", "1 TB", "2.9 GHz");
        System.out.println("Factory PC Config::" + pc);
        System.out.println("Factory Server Config::" + server);
    }

    private static void ReflectionSingletonTest() {
        EagerInitializedSingleton instanceOne = EagerInitializedSingleton.getInstance();
        EagerInitializedSingleton instanceTwo = null;
        try {

            Constructor[] constructors = EagerInitializedSingleton.class.getDeclaredConstructors();
            for (Constructor constructor : constructors) {
                //Below code will destroy the singleton pattern
                constructor.setAccessible(true);
                instanceTwo = (EagerInitializedSingleton) constructor.newInstance();

                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(instanceOne.hashCode());
        System.out.println(instanceTwo.hashCode());
    }

    private static void TestAbstractFactory() {
        creational.abstract_factory.computer.Computer pc = creational.abstract_factory.computer.ComputerFactory.getComputer(new PCFactory("2 GB", "500 GB", "2.4 GHz"));
        creational.abstract_factory.computer.Computer server = creational.abstract_factory.computer.ComputerFactory.getComputer(new ServerFactory("16 GB", "1 TB", "2.9 GHz"));
        System.out.println("AbstractFactory PC Config::" + pc);
        System.out.println("AbstractFactory Server Config::" + server);
    }

    private static void TestBuilderPattern() {
        //Using builder to get the object in a single line of code and
        //without any inconsistent state or argumentsmanagement issues
        new creational.build.Computer.ComputerBuilder("500 GB", "2 GB")
                .setBluetoothEnabled(true)
                .setGraphicsCardEnabled(true)
                .build();
    }

    private static void PrototypeTest() throws CloneNotSupportedException {
        Employees emps = new Employees();
        emps.loadData();

        //Use the clone method to get the Employee object
        Employees empsNew = (Employees) emps.clone();
        Employees empsNew1 = (Employees) emps.clone();
        List<String> list = empsNew.getEmpList();
        list.add("John");

        List<String> list1 = empsNew1.getEmpList();
        list1.remove("Pankaj");

        System.out.println("emps List: " + emps.getEmpList());
        System.out.println("empsNew List: " + list);
        System.out.println("empsNew1 List: " + list1);
    }

    private static void TestObjectAdapter() {
        SocketAdapter sockAdapter = new SocketObjectAdapterImpl();
        Volt v3 = getVolt(sockAdapter, 3);
        Volt v12 = getVolt(sockAdapter, 12);
        Volt v120 = getVolt(sockAdapter, 120);

        System.out.println("v3 volts using Object Adapter=" + v3.getVolts());
        System.out.println("v12 volts using Object Adapter=" + v12.getVolts());
        System.out.println("v120 volts using Object Adapter=" + v120.getVolts());
    }

    private static void TestClassAdapter() {
        SocketAdapter sockAdapter = new SocketClassAdapterImpl();
        Volt v3 = getVolt(sockAdapter, 3);
        Volt v12 = getVolt(sockAdapter, 12);
        Volt v120 = getVolt(sockAdapter, 120);
        System.out.println("v3 volts using Class Adapter=" + v3.getVolts());
        System.out.println("v12 volts using Class Adapter=" + v12.getVolts());
        System.out.println("v120 volts using Class Adapter=" + v120.getVolts());
    }

    private static Volt getVolt(SocketAdapter sockAdapter, int i) {
        switch (i) {
            case 3:
                return sockAdapter.get3Volt();
            case 12:
                return sockAdapter.get12Volt();
            case 120:
                return sockAdapter.get120Volt();
            default:
                return sockAdapter.get120Volt();
        }
    }

    private static void TestComposite() {
        Shape tri = new Triangle();
        Shape tri1 = new Triangle();
        Shape cir = new Circle();

        Drawing drawing = new Drawing();
        drawing.add(tri1);
        drawing.add(tri1);

        drawing.add(cir);

        drawing.draw("Red");

        drawing.clear();

        drawing.add(tri);
        drawing.add(cir);
        drawing.draw("Green");
    }


    private static void ProxyTest() {
        CommandExecutor executor = new CommandExecutorProxy("Pankaj", "wrong_pwd");
        try {
            executor.runCommand("ls -ltr");
            executor.runCommand(" rm -rf abc.pdf");
        } catch (Exception e) {
            System.out.println("Exception Message::" + e.getMessage());
        }


    }

    private static void FacadeTest() {
        String tableName = "Employee";

        //generating MySql HTML report and Oracle PDF report without using Facade
        Connection con = MySqlHelper.getMySqlDBConnection();
        MySqlHelper mySqlHelper = new MySqlHelper();
        mySqlHelper.generateMySqlHTMLReport(tableName, con);

        Connection con1 = OracleHelper.getOracleDBConnection();
        OracleHelper oracleHelper = new OracleHelper();
        oracleHelper.generateOraclePDFReport(tableName, con1);

        //generating MySql HTML report and Oracle PDF report using Facade
        HelperFacade.generateReport(HelperFacade.DBTypes.MYSQL, HelperFacade.ReportTypes.HTML, tableName);
        HelperFacade.generateReport(HelperFacade.DBTypes.ORACLE, HelperFacade.ReportTypes.PDF, tableName);
    }

    private static void BridgeTest() {
        structural.bridge.Shape tri = new structural.bridge.Triangle(new RedColor());
        tri.applyColor();

        structural.bridge.Shape pent = new Pentagon(new GreenColor());
        pent.applyColor();
    }

    private static void DecoratorTest() {
        Car sportsCar = new SportsCar(new BasicCar());
        sportsCar.assemble();
        System.out.println("\n*****");

        Car sportsLuxuryCar = new SportsCar(new LuxuryCar(new BasicCar()));
        sportsLuxuryCar.assemble();
    }

    private static void TemplateMethodTest() {
        HouseTemplate houseType = new WoodenHouse();

        //using template method
        houseType.buildHouse();
        System.out.println("************");

        houseType = new GlassHouse();

        houseType.buildHouse();
    }

    private static void MediatorTest() {
        ChatMediator mediator = new ChatMediatorImpl();
        User user1 = new UserImpl(mediator, "Pankaj");
        User user2 = new UserImpl(mediator, "Lisa");
        User user3 = new UserImpl(mediator, "Saurabh");
        User user4 = new UserImpl(mediator, "David");
        mediator.addUser(user1);
        mediator.addUser(user2);
        mediator.addUser(user3);
        mediator.addUser(user4);

        user1.send("Hi All");
    }

    private static void ChainResponsibilityTest() {
        ATMDispenseChain atmDispenser = new ATMDispenseChain();
        int amount = 530;
        // process the request
        atmDispenser.c1.dispense(new Currency(amount));
    }

    private static void ObserverTest() {
        //create subject
        MyTopic topic = new MyTopic();

        //create observers
        Observer obj1 = new MyTopicSubscriber("Obj1");
        Observer obj2 = new MyTopicSubscriber("Obj2");
        Observer obj3 = new MyTopicSubscriber("Obj3");

        //register observers to the subject
        topic.register(obj1);
        topic.register(obj2);
        topic.register(obj3);

        //attach observer to subject
        obj1.setSubject(topic);
        obj2.setSubject(topic);
        obj3.setSubject(topic);


        //check if any update is available
        obj1.update();

        //now send message to subject
        topic.postMessage("New Message");
    }

    private static void StrategyTest() {
        ShoppingCart cart = new ShoppingCart();

        Item item1 = new Item("1234", 10);
        Item item2 = new Item("5678", 40);

        cart.addItem(item1);
        cart.addItem(item2);

        //pay by paypal
        cart.pay(new PaypalStrategy("myemail@example.com", "mypwd"));

        //pay by credit card
        cart.pay(new CreditCardStrategy("Pankaj Kumar", "1234567890123456", "786", "12/15"));
    }

    private static void CommandTest() {
        //Creating the receiver object
        FileSystemReceiver fs = FileSystemReceiverUtil.getUnderlyingFileSystem();

        //creating command and associating with receiver
        OpenFileCommand openFileCommand = new OpenFileCommand(fs);

        //Creating invoker and associating with Command
        FileInvoker file = new FileInvoker(openFileCommand);

        //perform action on invoker object

        file.execute();

        WriteFileCommand writeFileCommand = new WriteFileCommand(fs);
        file = new FileInvoker(writeFileCommand);
        file.execute();

        CloseFileCommand closeFileCommand = new CloseFileCommand(fs);
        file = new FileInvoker(closeFileCommand);
        file.execute();
    }

    private static void StateTest() {
        TVContext context = new TVContext();
        State tvStartState = new TVStartState();
        State tvStopState = new TVStopState();

        context.setState(tvStartState);
        context.doAction();


        context.setState(tvStopState);
        context.doAction();
    }

    private static void VisitorTest() {
        ItemElement[] items = new ItemElement[]{
                new Book(20, "1234"), new Book(100, "5678"),
                new Fruit(10, 2, "Banana"),
                new Fruit(5, 5, "Apple")
        };

        int total = calculatePrice(items);
        System.out.println("Total Cost = " + total);
    }

    private static int calculatePrice(ItemElement[] items) {
        ShoppingCartVisitor visitor = new ShoppingCartVisitorImpl();
        int sum = 0;
        for (ItemElement item : items) {
            sum = sum + item.accept(visitor);
        }
        return sum;
    }

    private static void InterpreterTest() {
        String str1 = "28 in Binary";
        String str2 = "28 in Hexadecimal";

        InterpreterClient ec = new InterpreterClient(new InterpreterContext());
        System.out.println(str1 + "= " + ec.interpret(str1));
        System.out.println(str2 + "= " + ec.interpret(str2));
    }

    private static ChannelCollection populateChannels() {
        ChannelCollection channels = new ChannelCollectionImpl();
        channels.addChannel(new Channel(98.5, ChannelTypeEnum.ENGLISH));
        channels.addChannel(new Channel(99.5, ChannelTypeEnum.HINDI));
        channels.addChannel(new Channel(100.5, ChannelTypeEnum.FRENCH));
        channels.addChannel(new Channel(101.5, ChannelTypeEnum.ENGLISH));
        channels.addChannel(new Channel(102.5, ChannelTypeEnum.HINDI));
        channels.addChannel(new Channel(103.5, ChannelTypeEnum.FRENCH));
        channels.addChannel(new Channel(104.5, ChannelTypeEnum.ENGLISH));
        channels.addChannel(new Channel(105.5, ChannelTypeEnum.HINDI));
        channels.addChannel(new Channel(106.5, ChannelTypeEnum.FRENCH));

        return channels;
    }

    private static void IteratorTest() {
        ChannelCollection channels = populateChannels();
        ChannelIterator baseIterator = channels.iterator(ChannelTypeEnum.ALL);
        while (baseIterator.hasNext()) {
            Channel c = baseIterator.next();
            System.out.println(c.toString());
        }
        System.out.println("******");
        // Channel Type Iterator
        ChannelIterator englishIterator = channels.iterator(ChannelTypeEnum.ENGLISH);
        while (englishIterator.hasNext()) {
            Channel c = englishIterator.next();
            System.out.println(c.toString());
        }
    }

    private static void MementoTest() {
        FileWriterCaretaker caretaker = new FileWriterCaretaker();

        FileWriterUtil fileWriter = new FileWriterUtil("data.txt");
        fileWriter.write("First Set of Data\n");
        System.out.println(fileWriter+"\n\n");

        // lets save the file
        caretaker.save(fileWriter);
        //now write something else
        fileWriter.write("Second Set of Data\n");

        //checking file contents
        System.out.println(fileWriter+"\n\n");
        //lets undo to last save
        caretaker.undo(fileWriter);

        //checking file content again
        System.out.println(fileWriter+"\n\n");
    }
}
