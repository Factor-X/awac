import java.net.URL
import java.util

import com.google.gdata.client.spreadsheet.SpreadsheetService
import com.google.gdata.data.spreadsheet.{Cell, CellFeed, SpreadsheetEntry}
import scala.util.control.Breaks._

class DownloadTranslationsTask {

    private val DOCUMENT_URL = "https://spreadsheets.google.com/feeds/spreadsheets/0AiAtXT315RiWdFJxQ0FVMTlvNDhlamN2c2RGeEZWTVE"
    private var username = ""
    private var password = ""
    private var cells: List[List[Cell]] = _
    private var rowCount: Int = _
    private var colCount: Int = _

    def execute() {
        authenticate()
        download()
    }

    def authenticate() {

        val stdin = System.console()

        print("Email: ")
        username = stdin.readLine.trim

        print("Password: ")
        password = stdin.readPassword.mkString.trim
    }


    def getValue(row: Int, column: Int): String = {

        val cell = cells(row)(column)

        if (cell == null)
            null
        else
            cell.getValue
    }

    def download() {

        // GDATA NOT SUPPORTED BY SCALA YET

        val service = new SpreadsheetService("Google Spreadsheet")
        service.setUserCredentials(username, password)

        val metafeedUrl = new URL(DOCUMENT_URL)
        val spreadsheet = service.getEntry(metafeedUrl, classOf[SpreadsheetEntry])

        val listFeedUrl = spreadsheet.getWorksheets.get(0).getCellFeedUrl

        val cFeed = service.getFeed(listFeedUrl, classOf[CellFeed])

        fillCellsFromFeed(cFeed)

        var startRow = 0

        val languages = new util.HashMap[String, Integer]()

        // Find languages
        for (r <- 0 to rowCount) {
            var v = getValue(r, 0)
            if (v != null && v.equals("CODE")) {
                startRow = r + 3
                for (c <- 2 to colCount) {
                    v = getValue(r, c)
                    if (v != null && v.trim().length() > 0) {
                        languages.put(v.trim().toLowerCase, c)
                    }
                }
                break()
            }
        }


    }


    private def fillCellsFromFeed(cFeed: CellFeed) {
/*
        cells = List(List[Cell]]()
        rowCount = cFeed.getRowCount
        colCount = cFeed.getColCount
        for (r <- 0 until rowCount) {
            val row = new util.ArrayList[Cell]()
            cells.add(row)
            for (c <- 0 until colCount) {
                row.add(null)
            }
        }
        for (row <- cFeed.getEntries) {
            val cell = row.getCell
            cells.get(cell.getRow - 1).set(cell.getCol - 1, cell)
        }*/
    }
}
