package sample;

import org.aspectj.weaver.tools.cache.AsynchronousFileCacheBacking.AbstractCommand;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;

public class TestCommand extends HystrixCommand<String> {
	private final String name;
	static com.netflix.hystrix.HystrixCommandProperties.Setter setter = null;
	static {
		setter = HystrixCommandProperties.Setter();
		setter.withExecutionIsolationThreadTimeoutInMilliseconds(1000000);
		setter.withExecutionIsolationStrategy(ExecutionIsolationStrategy.THREAD);
		setter.withExecutionIsolationSemaphoreMaxConcurrentRequests(1);
	}
	public TestCommand(String name) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("HelloWorldGroup"))
				/* 配置依赖超时时间,500毫秒 */
				.andCommandPropertiesDefaults(setter));
		this.name = name;
	}

	@Override
	protected String getFallback() {
		return "exeucute Falled";
	}

	@Override
	protected String run() throws Exception {
		// sleep 1 秒,调用会超时
		Thread.sleep(1000);
		return "Hello " + name + " thread:" + Thread.currentThread().getName();
	}

	public static void main(String[] args) throws Exception {
//		com.netflix.hystrix.AbstractCommand a;
		for (int i = 0; i < 1; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					TestCommand command = new TestCommand("test-" + 1);
					String result = command.execute();
					System.err.println("---------------->"+result);
				}
			}).start();;
		
		}
		
	}
}