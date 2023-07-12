import 'package:flying_redux/flying_redux.dart';

enum ToDoEditAction { onDone, onChangeTheme }

class ToDoEditActionCreator {
  static Action onDone() {
    return const Action(ToDoEditAction.onDone);
  }

  static Action onChangeTheme() {
    return const Action(ToDoEditAction.onChangeTheme);
  }
}
