package com.delgrau.expensetracker;

import com.delgrau.expensetracker.model.Amount;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(name = "expense-tracker", /* mixinStandardHelpOptions = true,*/ version = "1.0",
    description = "Personal Expense Tracker via command line.")
public class ExpenseTracker implements Callable<Integer> {

    @Option(names = {"-h", "--help"}, usageHelp = true, description = "Exibe esta mensagem de ajuda")
    private boolean helpRequested;

    @Option(names = {"-V", "--version"}, versionHelp = true, description = "Exibe a versão do programa")
    private boolean versionRequested;

    @Option(names = {"-d", "--description"}, required = true)
    private String description;

    @Option(names = {"-v", "--value"}, required = true)
    private Amount value;

    public String getDescription() {
        return description;
    }

    public double getValue() {
        return value.getAmount();
    }

    public long getCents() {
        return value.getCents();
    }

    @Override
    public Integer call() throws Exception {
        try {
            System.out.println("Processando...");
            System.out.println("Descricao: " + getDescription());
            System.out.println("Valor: " + getValue() +
                    " (ou " + getCents() + " centavos).");
            return 0;
        } catch (Exception e) {
            System.out.println("Oops, algo deu errado...");
        }
        return 500;
    }

    public static void main(String[] args) {
        CommandLine cmd = new CommandLine(new ExpenseTracker());

        cmd.registerConverter(Amount.class, s -> {
            double val = Double.parseDouble(s.replace(",", "."));
            return Amount.fromCurrency(val);
        });

        var exitCode = cmd.execute(args);
        System.exit(exitCode);
    }
}