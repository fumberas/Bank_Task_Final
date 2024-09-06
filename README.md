How to Use the API:
Build the project using Maven:  
- mvn clean install

Run the application:
- mvn spring-boot:run


Enrich Trades
Endpoint: /api/v1/enrich

Sample HTTP Request:

curl -X POST -F "tradeFile=@/path/to/your/trades.csv" http://localhost:8080/api/v1/enrich -o response.json -w "\nTime taken: %{time_total} seconds\nDNS lookup time: %{time_namelookup} seconds\nConnection time: %{time_connect} seconds\nStart transfer time: %{time_starttransfer} seconds\n"

Replace /path/to/your/trades.csv with the actual path to your trades.csv file. This command will send the file to the API, save the response to response.json, and print timing information.

Ideas for Improvement:
- Improve error handling and logging to provide more detailed information about failures.
- Add product read at startup to make request faster.