import eel
import other
import os


application_path = os.path.dirname(os.path.abspath(__file__))

eel.init(os.path.join(application_path, 'view'))

@eel.expose
def process_input(userInput: int):
    try:
        other.task(int(userInput))
    except:
        return "error during processing"
    return f"Python received: {userInput}"

eel.start('html/index.html')
