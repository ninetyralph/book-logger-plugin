import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class BookLogger extends JavaPlugin implements Listener {

    private final List<String> books = new ArrayList<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onBookSign(PlayerEditBookEvent event) {
        BookMeta meta = event.getNewBookMeta();
        if (meta == null) return;

        books.add(
                "Title: " + meta.getTitle() +
                "\nAuthor: " + meta.getAuthor() +
                "\nPages: " + meta.getPages()
        );
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Players only.");
            return true;
        }

        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("viewbooks")) {

            if (books.isEmpty()) {
                player.sendMessage("No books saved yet.");
                return true;
            }

            player.sendMessage("=== Books ===");

            for (String book : books) {
                player.sendMessage(book);
            }

            return true;
        }

        return false;
    }
}
