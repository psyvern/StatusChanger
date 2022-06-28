## Details

Changes the server's MOTD and icon at a fixed rate.  
The new status is chosen randomly between the provided statuses.

## Configuration

The configuration file looks like this:

```
{
    "delay": 3600,
    "statuses": {
        "Example, using the default icon": "server-icon.png",
        "§4Custom formatting\n§9example": "diamond.png",
        "You can also use subfolders!": "icons/creeper.png",
    }
}
```

- `delay`: how often to change the status in seconds, defaults to 3600 (1 hour)
- `statuses`: the custom statuses to display, in a `"motd": "icon"` format
    - `motd`: the message-of-the-day. Can contain [Formatting codes](https://minecraft.fandom.com/wiki/Formatting_codes)
    - `icon`: the path to the desired file. Can point to subfolders. The images must be 64x64 .png files

## Examples

![](https://cdn-raw.modrinth.com//data/UWZnc5M2/images/dd261e000200970098bbb5177d3b5186407c446c.png)
![](https://cdn-raw.modrinth.com//data/UWZnc5M2/images/ddd8f57750ac07c7b3beaedc67b929e40d677e25.png)
