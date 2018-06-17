package yiqun;

/**
 * Created by maqiyue on 2018/5/28
 */
import junit.framework.TestCase;


/**
 * @author Administrator
 *
 */
public class TestRouterService extends TestCase {
    public void testcase1() {
        RouterService routerService = new RouterService();
        String routerRule = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                +"<routertable><router><num></num><net>router1</net></router></routertable>";
        assertEquals(false, routerService.loadRule(routerRule));
    }

    public void testcase2() {
        RouterService routerService = new RouterService();
        String routerRule = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                +"<routertable><router><num>13567</num><net></net></router></routertable>";
        assertEquals(false, routerService.loadRule(routerRule));
    }

    public void testcase3() {
        RouterService routerService = new RouterService();
        String routerRule = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                +"<routertable><router><num>13567</num><net>router1</net></router>"
                +"<router><num>13567</num><net>router2</net></router></routertable>";
        assertEquals(false, routerService.loadRule(routerRule));
    }

    public void testcase4() {
        RouterService routerService = new RouterService();
        String routerRule = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                +"<routertable><router><num>13562</num><net>router1</net></router></routertable>";
        assertEquals(true, routerService.loadRule(routerRule));
        assertEquals("router1", routerService.selRouter("13562779908"));
    }

    public void testcase5() {
        RouterService routerService = new RouterService();
        String routerRule = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                +"<routertable><router><num>13562</num><net>router1</net></router>"
                +"<router><num>135627</num><net>router2</net></router></routertable>";
        assertEquals(true, routerService.loadRule(routerRule));
        assertEquals("router2", routerService.selRouter("13562779908"));
    }
}