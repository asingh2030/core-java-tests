package org.example.thread.producer_consumer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 * Example is where we want to download some file which can be downloaded from various mirrors.
 * In that case we can quickly get the response from the server which is located closest to us.
 * In that case we can get the first available result and discard the others.
 * By using CompletionService will take first server where we found the result/ file and discard others.
 * We need not to wait until to submit all servers tasks.
 */
public class FileDownloadTest {
    public static void main(String[] args) {

    }
    private static class Result{

    }

    public void solve(Executor e, Collection<Callable<Result>> solvers)
            throws InterruptedException {
        CompletionService<Result> ecs = new ExecutorCompletionService<>(e);
        int n = solvers.size();
        List<Future<Result>> futures = new ArrayList<>(n);
        Result result = null;
        try {
            for (Callable<Result> s : solvers)
                futures.add(ecs.submit(s));
            for (int i = 0; i < n; ++i) {
                try {
                    Result r = ecs.take().get();
                    if (r != null) {
                        result = r;
                        break;
                    }
                } catch(ExecutionException ignore) {}
            }
        }
        finally {
            for (Future<Result> f : futures)
                f.cancel(true);
        }

        if (result != null)
            //use(result);
            System.out.println("Got result "+result);

    }

}
