package com.eduludo.persistence;

import com.eduludo.model.Difficulty;
import com.eduludo.model.GameRecord;
import com.eduludo.model.PlayerColor;
import com.eduludo.model.Question;
import com.eduludo.model.QuestionLevel;
import com.eduludo.security.CryptoBox;
import com.eduludo.testing.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.eduludo.testing.Assert.assertEquals;
import static com.eduludo.testing.Assert.assertFalse;
import static com.eduludo.testing.Assert.assertThrows;
import static com.eduludo.testing.Assert.assertTrue;

/** JSON, encrypted question storage, and the CSV history. */
public class PersistenceTest {

    @Test
    void jsonRoundTripsAllValueTypes() {
        String json = "{\"a\": [1, 2.5, true, false, null, \"x\\\"y\\n\\u00e9\"], \"b\": {}}";
        Object parsed = MiniJson.parse(json);
        Object again = MiniJson.parse(MiniJson.write(parsed));
        assertEquals(parsed, again, "write then parse gives the same value");
        List<?> a = (List<?>) ((Map<?, ?>) parsed).get("a");
        assertEquals("x\"y\né", a.get(5), "escapes decoded");
        assertThrows(IllegalArgumentException.class, () -> MiniJson.parse("{\"a\": }"), "invalid JSON rejected");
        assertThrows(IllegalArgumentException.class, () -> MiniJson.parse("[1, 2] extra"), "trailing text rejected");
    }

    @Test
    void theDefaultQuestionBankIsValidAndLargeEnough() {
        List<Question> qs = QuestionJson.fromJson(EncryptedQuestionRepository.readDefaultResource());
        assertTrue(qs.size() >= 30, "at least 30 questions (found " + qs.size() + ")");
        for (QuestionLevel level : QuestionLevel.values()) {
            long n = qs.stream().filter(q -> q.level() == level).count();
            assertTrue(n >= 10, level + " has at least 10 questions");
        }
        assertEquals((long) qs.size(), qs.stream().map(Question::id).distinct().count(), "ids are unique");
        int[] correctCounts = new int[4];
        qs.forEach(q -> correctCounts[q.correctIndex()]++);
        for (int c : correctCounts) {
            assertTrue(c >= 5, "the correct answer is spread over all 4 positions");
        }
    }

    @Test
    void questionsAreStoredEncryptedAndCreatedFromDefaultsOnFirstRun() throws IOException {
        Path file = Files.createTempDirectory("eduludo-q").resolve("questions.dat");
        CryptoBox box = new CryptoBox("k".toCharArray());
        EncryptedQuestionRepository repo = new EncryptedQuestionRepository(file, box);
        List<Question> first = repo.loadAll();
        assertTrue(Files.exists(file), "file created on first run");
        String raw = Files.readString(file);
        assertFalse(raw.contains("Scrum"), "questions are not readable in the file");
        assertFalse(raw.contains("\"correct\""), "answers are not readable in the file");
        List<Question> second = new EncryptedQuestionRepository(file, box).loadAll();
        assertEquals(first, second, "same questions after reload");
    }

    @Test
    void historyIsSavedAndReadBackNewestFirst() throws IOException {
        Path file = Files.createTempDirectory("eduludo-h").resolve("history.csv");
        CsvHistoryRepository repo = new CsvHistoryRepository(file);
        GameRecord older = new GameRecord("a1", LocalDateTime.of(2026, 1, 1, 10, 0), Difficulty.EASY, 300,
                List.of(new GameRecord.RankedPlayer(1, "Dana, \"the\" | best: 1", PlayerColor.RED, false),
                        new GameRecord.RankedPlayer(2, "Bot", PlayerColor.BLUE, true)));
        GameRecord newer = new GameRecord("b2", LocalDateTime.of(2026, 2, 1, 10, 0), Difficulty.HARD, 3725,
                List.of(new GameRecord.RankedPlayer(1, "Omar", PlayerColor.GREEN, false),
                        new GameRecord.RankedPlayer(2, "Lia", PlayerColor.YELLOW, false),
                        new GameRecord.RankedPlayer(3, "Noa", PlayerColor.RED, false)));
        repo.save(older);
        repo.save(newer);
        List<GameRecord> all = new CsvHistoryRepository(file).findAll();
        assertEquals(2, all.size(), "two games");
        assertEquals(newer, all.get(0), "newest first");
        assertEquals(older, all.get(1), "names with commas, quotes, | and : survive");
        assertEquals("1:02:05", all.get(0).formattedDuration(), "duration format");
    }

    @Test
    void aDamagedHistoryLineIsSkipped() throws IOException {
        Path file = Files.createTempDirectory("eduludo-h2").resolve("history.csv");
        CsvHistoryRepository repo = new CsvHistoryRepository(file);
        repo.save(new GameRecord("ok", LocalDateTime.of(2026, 1, 1, 10, 0), Difficulty.MEDIUM, 60,
                List.of(new GameRecord.RankedPlayer(1, "A", PlayerColor.RED, false),
                        new GameRecord.RankedPlayer(2, "B", PlayerColor.GREEN, false))));
        Files.writeString(file, "garbage,line\n", java.nio.file.StandardOpenOption.APPEND);
        assertEquals(1, repo.findAll().size(), "the good line is still read");
    }
}
