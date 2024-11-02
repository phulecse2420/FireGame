package logan.neo4j.checker;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ExistCheckerTest {

    @Test
    void testExist() {
        assertFalse(ExistChecker.check("a"));
        assertTrue(ExistChecker.check("a"));
        assertFalse(ExistChecker.check("b"));
        assertTrue(ExistChecker.check("b"));
        assertFalse(ExistChecker.check("abcd"));
        assertFalse(ExistChecker.check("abdc"));
    }
}
