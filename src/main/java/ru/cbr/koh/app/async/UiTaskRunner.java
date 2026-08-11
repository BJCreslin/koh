package ru.cbr.koh.app.async;

import ru.cbr.koh.app.error.ErrorHandler;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class UiTaskRunner {

    private final ErrorHandler errorHandler;

    public UiTaskRunner(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public void runWithProgress(Window parent,
                                String title,
                                String progressText,
                                ThrowingRunnable task,
                                Runnable onSuccess,
                                String errorMessage) {
        runWithProgressResult(
                parent,
                title,
                progressText,
                () -> {
                    task.run();
                    return null;
                },
                ignored -> {
                    if (onSuccess != null) {
                        onSuccess.run();
                    }
                },
                errorMessage
        );
    }

    public <T> void runWithProgressResult(Window parent,
                                          String title,
                                          String progressText,
                                          ThrowingSupplier<T> task,
                                          Consumer<T> onSuccess,
                                          String errorMessage) {
        JDialog progressDialog = createProgressDialog(parent, title, progressText);

        SwingWorker<T, Void> worker = new SwingWorker<>() {
            @Override
            protected T doInBackground() throws Exception {
                return task.get();
            }

            @Override
            protected void done() {
                progressDialog.dispose();
                try {
                    T result = get();
                    if (onSuccess != null) {
                        onSuccess.accept(result);
                    }
                } catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                    errorHandler.handle(
                            parent,
                            errorMessage,
                            new IllegalStateException("Операция была прервана", interruptedException));
                } catch (ExecutionException executionException) {
                    Throwable cause = executionException.getCause() == null
                            ? executionException
                            : executionException.getCause();
                    Exception exception = cause instanceof Exception
                            ? (Exception) cause
                            : new RuntimeException(cause);
                    errorHandler.handle(parent, errorMessage, exception);
                }
            }
        };

        worker.execute();
        progressDialog.setVisible(true);
    }

    private JDialog createProgressDialog(Window parent, String title, String progressText) {
        JDialog dialog = new JDialog(parent, title, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel label = new JLabel(progressText);
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);

        panel.add(label, BorderLayout.NORTH);
        panel.add(progressBar, BorderLayout.CENTER);

        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        return dialog;
    }

    @FunctionalInterface
    public interface ThrowingRunnable {
        void run() throws Exception;
    }

    @FunctionalInterface
    public interface ThrowingSupplier<T> {
        T get() throws Exception;
    }
}
